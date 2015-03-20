package il.ac.tau.cs.sw1.trivia;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.rmi.CORBA.Util;
import javax.swing.text.Utilities;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class TriviaGUI {

	private static final int MAX_ERRORS = 3;
	private Shell shell;
	private Label scoreLabel;
	private Composite questionPanel;
	private Font boldFont;
	private String lastAnswer = "";
	private static String filepath;
	private static List<List<String>> questions;
	private int current_question_number;
	private int current_score =0;
	private int correct_answers =0;
	private int wrong_answers =0;

	public void open() {
		createShell();
		runApplication();
	}

	/**
	 * Creates the widgets of the application main window
	 */
	private void createShell() {
		Display display = Display.getDefault();
		shell = new Shell(display);
		shell.setText("Trivia");

		// window style
		Rectangle monitor_bounds = shell.getMonitor().getBounds();
		shell.setSize(new Point(monitor_bounds.width / 3,
				monitor_bounds.height / 4));
		shell.setLayout(new GridLayout());

		FontData fontData = new FontData();
		fontData.setStyle(SWT.BOLD);
		boldFont = new Font(shell.getDisplay(), fontData);
		
		//create window panels
		createFileLoadingPanel();
		createScorePanel();
		createQuestionPanel();
		
		
	}
	
	
	private void buildquestions() throws IOException{
		List<List<String>> output = new ArrayList<>();
		List<String> sub_output = new ArrayList<>();
		byte[] allBytes = Files.readAllBytes(Paths.get(filepath));
		String temp = new String(allBytes);
		sub_output = Arrays.asList(temp.split("\n"));
		for(int i=0 ; i<sub_output.size();i++){
			output.add(Arrays.asList(sub_output.get(i).split("\t")));
		}
		Collections.shuffle(output);
		questions = output;
	}

	/**
	 * Creates the widgets of the form for trivia file selection
	 */
	private void createFileLoadingPanel() {
		final Composite fileSelection = new Composite(shell, SWT.NULL);
		fileSelection.setLayoutData(GUIUtils.createFillGridData(1));
		fileSelection.setLayout(new GridLayout(4, false));

		final Label label = new Label(fileSelection, SWT.NONE);
		label.setText("Enter trivia file path: ");

		// text field to enter the file path
		final Text filePathField = new Text(fileSelection, SWT.SINGLE
				| SWT.BORDER);
		filePathField.setLayoutData(GUIUtils.createFillGridData(1));


		// "Browse" button 
		final Button browseButton = new Button(fileSelection,
				SWT.PUSH);
		browseButton.setText("Browse");
		browseButton.addListener(SWT.Selection,new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				filePathField.setText(GUIUtils.getFilePathFromFileDialog(shell));
				filepath = filePathField.getText();
			}
		});
		

		// "Play!" button
		final Button playButton = new Button(fileSelection, SWT.PUSH);
		playButton.setText("Play!");
		playButton.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				filepath = filePathField.getText();
				try {
					buildquestions();
				} catch (IOException e) {
					GUIUtils.showErrorDialog(shell, "File missing");
					return;
				}
				if(!kosher_filetype()){
					GUIUtils.showErrorDialog(shell, "wrong file format");
				}
				else if(!kosher_filestructure()){
					GUIUtils.showErrorDialog(shell, "wrong file structure");
				}
				else{
					current_score =0;
					wrong_answers =0;
					correct_answers =0;
					scoreLabel.setText("0");
					lets_play();
				}
			}
		});
	}
	
	// updates the question, called in case of pressing play, submitting answer or pressing pass;
	private void lets_play(){
		if(wrong_answers == MAX_ERRORS || questions.size() == 0){
			GUIUtils.showInfoDialog(shell, "GAME OVER", "Your final Score is " + current_score + " after " + (wrong_answers + correct_answers )+ " questions .");
			
		}
		else{
			Random rnd = new Random();
			int rndint = rnd.nextInt(questions.size());
			String question = questions.get(rndint).get(0);
			List<String> answers = new ArrayList<>(questions.get(rndint).subList(1,5));
			Collections.shuffle(answers);
			updateQuestionPanel(question,answers);
			current_question_number = rndint;
		}
	}

	/**
	 * Creates the panel that displays the current score
	 */
	private void createScorePanel() {
		Composite scorePanel = new Composite(shell, SWT.BORDER);
		scorePanel.setLayoutData(GUIUtils.createFillGridData(1));
		scorePanel.setLayout(new GridLayout(2, false));

		final Label label = new Label(scorePanel, SWT.NONE);
		label.setText("Total score: ");

		// The label which displays the score; initially empty
		scoreLabel = new Label(scorePanel, SWT.NONE);
		scoreLabel.setLayoutData(GUIUtils.createFillGridData(1));
	}

	/**
	 * Creates the panel that displays the questions, as soon as the game starts.
	 * See the updateQuestionPanel for creating the question and answer buttons
	 */
	private void createQuestionPanel() {
		questionPanel = new Composite(shell, SWT.BORDER);
		questionPanel.setLayoutData(new GridData(GridData.FILL,
				GridData.FILL, true, true));
		questionPanel.setLayout(new GridLayout(2, true));

		//Initially, only displays a message
		Label label = new Label(questionPanel, SWT.NONE);
		label.setText("No question to display, yet.");
		label.setLayoutData(GUIUtils.createFillGridData(2));
	}

	/**
	 * Serves to display the question and answer buttons
	 */
	private void updateQuestionPanel(String question,
			List<String> answers) {
		
		// clear the question panel
		Control[] children = questionPanel.getChildren();
		for (Control control : children) {
			control.dispose();
		}

		// create the instruction label
		Label instructionLabel = new Label(questionPanel, SWT.CENTER
				| SWT.WRAP);
		instructionLabel.setText(lastAnswer 
				+ "Answer the following question:");
		instructionLabel
				.setLayoutData(GUIUtils.createFillGridData(2));

		// create the question label
		Label questionLabel = new Label(questionPanel, SWT.CENTER
				| SWT.WRAP);
		questionLabel.setText(question);
		questionLabel.setFont(boldFont);
		questionLabel.setLayoutData(GUIUtils.createFillGridData(2));

		// create the answer buttons
		for (int i = 0; i < 4; i++) {
			Button answerButton = new Button(questionPanel, SWT.PUSH
					| SWT.WRAP);
			answerButton.setText(answers.get(i));
			GridData answerLayoutData = GUIUtils
					.createFillGridData(1);
			answerLayoutData.verticalAlignment = SWT.FILL;
			answerButton.setLayoutData(answerLayoutData);
			answerButton.addListener(SWT.Selection, new Listener() {
				
				@Override
				public void handleEvent(Event arg0) {
					if(answerButton.getText().equals(questions.get(current_question_number).get(1))){
						current_score = current_score + 3;
						lastAnswer = "Correct!";
						correct_answers++;
						scoreLabel.setText(Integer.toString(current_score));
						questions.remove(current_question_number);
					}
					else{
						current_score = current_score -1;
						lastAnswer = "Wrong...";
						wrong_answers++;
						scoreLabel.setText(Integer.toString(current_score));
						questions.remove(current_question_number);
					}
					lets_play();
				}
			});
		}

		// create the "Pass" button to skip a question
		Button passButton = new Button(questionPanel, SWT.PUSH);
		passButton.setText("Pass");
		GridData data = new GridData(GridData.CENTER,
				GridData.CENTER, true, false);
		data.horizontalSpan = 2;
		passButton.setLayoutData(data);

		// two operations to make the new widgets display properly
		questionPanel.pack();
		questionPanel.getParent().layout();
		passButton.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				if(wrong_answers != MAX_ERRORS && questions.size() !=0){
					questions.remove(current_question_number);
					lets_play();
				}
			}
		});
	}
	
	private static boolean kosher_filetype(){
		String filetype = filepath.split("\\.")[1];
		if(filetype.equals("txt")){
			return true;
		}
		else{
			return false;
		}
	}
	
	private static boolean kosher_filestructure(){
		for(List<String> i:questions){
			if(i.size() < 5){
				return false;
			}
		}
		return true;
	}

	/**
	 * Opens the main window and executes the event loop of the application
	 */
	private void runApplication() {
		shell.open();
		Display display = shell.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
		boldFont.dispose();
	}
}

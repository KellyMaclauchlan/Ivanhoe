package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import config.Config;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTextArea;

public class Guilayout extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Guilayout frame = new Guilayout();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Guilayout() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1360, 840);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 40, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{ 40, 40, 40, 40, 40, 0, 0, 0, 0, 0, 0, 0, 137, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel token11 = new JLabel("");
		token11.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token11 = new GridBagConstraints();
		gbc_token11.insets = new Insets(0, 0, 5, 5);
		gbc_token11.gridx = 5;
		gbc_token11.gridy = 1;
		getContentPane().add(token11, gbc_token11);
		
		JLabel sheild1 = new JLabel("");
		GridBagConstraints gbc_sheild1 = new GridBagConstraints();
		gbc_sheild1.insets = new Insets(0, 0, 5, 5);
		gbc_sheild1.gridx = 7;
		gbc_sheild1.gridy = 1;
		getContentPane().add(sheild1, gbc_sheild1);
		
		JLabel stun1 = new JLabel("");
		GridBagConstraints gbc_stun1 = new GridBagConstraints();
		gbc_stun1.insets = new Insets(0, 0, 5, 5);
		gbc_stun1.gridx = 9;
		gbc_stun1.gridy = 1;
		getContentPane().add(stun1, gbc_stun1);
		
		JLabel token21 = new JLabel("");
		token21.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token21 = new GridBagConstraints();
		gbc_token21.insets = new Insets(0, 0, 5, 5);
		gbc_token21.gridx = 10;
		gbc_token21.gridy = 1;
		getContentPane().add(token21, gbc_token21);
		
		JLabel sheild2 = new JLabel("");
		GridBagConstraints gbc_sheild2 = new GridBagConstraints();
		gbc_sheild2.insets = new Insets(0, 0, 5, 5);
		gbc_sheild2.gridx = 12;
		gbc_sheild2.gridy = 1;
		getContentPane().add(sheild2, gbc_sheild2);
		
		JLabel stun2 = new JLabel("");
		GridBagConstraints gbc_stun2 = new GridBagConstraints();
		gbc_stun2.insets = new Insets(0, 0, 5, 5);
		gbc_stun2.gridx = 14;
		gbc_stun2.gridy = 1;
		getContentPane().add(stun2, gbc_stun2);
		
		JLabel token31 = new JLabel("");
		token31.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token31 = new GridBagConstraints();
		gbc_token31.insets = new Insets(0, 0, 5, 5);
		gbc_token31.gridx = 15;
		gbc_token31.gridy = 1;
		getContentPane().add(token31, gbc_token31);
		
		JLabel sheild3 = new JLabel("");
		GridBagConstraints gbc_sheild3 = new GridBagConstraints();
		gbc_sheild3.insets = new Insets(0, 0, 5, 5);
		gbc_sheild3.gridx = 17;
		gbc_sheild3.gridy = 1;
		getContentPane().add(sheild3, gbc_sheild3);
		
		JLabel stun3 = new JLabel("");
		GridBagConstraints gbc_stun3 = new GridBagConstraints();
		gbc_stun3.anchor = GridBagConstraints.NORTH;
		gbc_stun3.insets = new Insets(0, 0, 5, 5);
		gbc_stun3.gridx = 19;
		gbc_stun3.gridy = 1;
		getContentPane().add(stun3, gbc_stun3);
		
		JLabel token41 = new JLabel("");
		token41.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token41 = new GridBagConstraints();
		gbc_token41.insets = new Insets(0, 0, 5, 5);
		gbc_token41.gridx = 20;
		gbc_token41.gridy = 1;
		getContentPane().add(token41, gbc_token41);
		
		JLabel sheild4 = new JLabel("");
		GridBagConstraints gbc_sheild4 = new GridBagConstraints();
		gbc_sheild4.insets = new Insets(0, 0, 5, 5);
		gbc_sheild4.gridx = 22;
		gbc_sheild4.gridy = 1;
		getContentPane().add(sheild4, gbc_sheild4);
		
		JLabel stun4 = new JLabel("");
		GridBagConstraints gbc_stun4 = new GridBagConstraints();
		gbc_stun4.insets = new Insets(0, 0, 5, 5);
		gbc_stun4.gridx = 24;
		gbc_stun4.gridy = 1;
		getContentPane().add(stun4, gbc_stun4);
		
		JLabel token51 = new JLabel("");
		token51.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token51 = new GridBagConstraints();
		gbc_token51.insets = new Insets(0, 0, 5, 5);
		gbc_token51.gridx = 25;
		gbc_token51.gridy = 1;
		getContentPane().add(token51, gbc_token51);
		
		JLabel sheild5 = new JLabel("");
		GridBagConstraints gbc_sheild5 = new GridBagConstraints();
		gbc_sheild5.insets = new Insets(0, 0, 5, 5);
		gbc_sheild5.gridx = 27;
		gbc_sheild5.gridy = 1;
		getContentPane().add(sheild5, gbc_sheild5);
		
		JLabel stun5 = new JLabel("");
		GridBagConstraints gbc_stun5 = new GridBagConstraints();
		gbc_stun5.insets = new Insets(0, 0, 5, 5);
		gbc_stun5.gridx = 29;
		gbc_stun5.gridy = 1;
		getContentPane().add(stun5, gbc_stun5);
		
		JButton p1d = new JButton("");
		p1d.setIcon(ResourceLoader.loadImage(Config.IMG_BACK));
		GridBagConstraints gbc_p1d = new GridBagConstraints();
		gbc_p1d.gridheight = 3;
		gbc_p1d.gridwidth = 3;
		gbc_p1d.insets = new Insets(0, 0, 5, 5);
		gbc_p1d.gridx = 7;
		gbc_p1d.gridy = 2;
		getContentPane().add(p1d, gbc_p1d);
		
		JLabel token12 = new JLabel("");
		token12.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token12 = new GridBagConstraints();
		gbc_token12.insets = new Insets(0, 0, 5, 5);
		gbc_token12.gridx = 5;
		gbc_token12.gridy = 2;
		getContentPane().add(token12, gbc_token12);
		
		JLabel token22 = new JLabel("");
		token22.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token22 = new GridBagConstraints();
		gbc_token22.insets = new Insets(0, 0, 5, 5);
		gbc_token22.gridx = 10;
		gbc_token22.gridy = 2;
		getContentPane().add(token22, gbc_token22);
		
		JButton p2d = new JButton("");
		p2d.setIcon(ResourceLoader.loadImage(Config.IMG_IVANHOE));
		GridBagConstraints gbc_p2d = new GridBagConstraints();
		gbc_p2d.gridwidth = 3;
		gbc_p2d.gridheight = 3;
		gbc_p2d.insets = new Insets(0, 0, 5, 5);
		gbc_p2d.gridx = 12;
		gbc_p2d.gridy = 2;
		getContentPane().add(p2d, gbc_p2d);
		
		JLabel token32 = new JLabel("");
		token32.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token32 = new GridBagConstraints();
		gbc_token32.insets = new Insets(0, 0, 5, 5);
		gbc_token32.gridx = 15;
		gbc_token32.gridy = 2;
		getContentPane().add(token32, gbc_token32);
		
		JButton p3d = new JButton("");
		p3d.setIcon(ResourceLoader.loadImage(Config.IMG_IVANHOE));
		GridBagConstraints gbc_p3d = new GridBagConstraints();
		gbc_p3d.gridheight = 3;
		gbc_p3d.gridwidth = 3;
		gbc_p3d.insets = new Insets(0, 0, 5, 5);
		gbc_p3d.gridx = 17;
		gbc_p3d.gridy = 2;
		getContentPane().add(p3d, gbc_p3d);
		
		JLabel token42 = new JLabel("");
		token42.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token42 = new GridBagConstraints();
		gbc_token42.insets = new Insets(0, 0, 5, 5);
		gbc_token42.gridx = 20;
		gbc_token42.gridy = 2;
		getContentPane().add(token42, gbc_token42);
		
		JButton p4d = new JButton("");
		p4d.setIcon(ResourceLoader.loadImage(Config.IMG_IVANHOE));
		GridBagConstraints gbc_p4d = new GridBagConstraints();
		gbc_p4d.gridheight = 3;
		gbc_p4d.gridwidth = 3;
		gbc_p4d.insets = new Insets(0, 0, 5, 5);
		gbc_p4d.gridx = 22;
		gbc_p4d.gridy = 2;
		getContentPane().add(p4d, gbc_p4d);
		
		JLabel token52 = new JLabel("");
		token52.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token52 = new GridBagConstraints();
		gbc_token52.insets = new Insets(0, 0, 5, 5);
		gbc_token52.gridx = 25;
		gbc_token52.gridy = 2;
		getContentPane().add(token52, gbc_token52);
		
		JButton p5d = new JButton("");
		p5d.setIcon(ResourceLoader.loadImage(Config.IMG_IVANHOE));
		GridBagConstraints gbc_p5d = new GridBagConstraints();
		gbc_p5d.gridheight = 3;
		gbc_p5d.gridwidth = 3;
		gbc_p5d.insets = new Insets(0, 0, 5, 5);
		gbc_p5d.gridx = 27;
		gbc_p5d.gridy = 2;
		getContentPane().add(p5d, gbc_p5d);
		
		JLabel token13 = new JLabel("");
		token13.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token13 = new GridBagConstraints();
		gbc_token13.insets = new Insets(0, 0, 5, 5);
		gbc_token13.gridx = 5;
		gbc_token13.gridy = 3;
		getContentPane().add(token13, gbc_token13);
		
		JLabel token23 = new JLabel("");
		token23.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token23 = new GridBagConstraints();
		gbc_token23.insets = new Insets(0, 0, 5, 5);
		gbc_token23.gridx = 10;
		gbc_token23.gridy = 3;
		getContentPane().add(token23, gbc_token23);
		
		JLabel token33 = new JLabel("");
		//token33.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token33 = new GridBagConstraints();
		gbc_token33.insets = new Insets(0, 0, 5, 5);
		gbc_token33.gridx = 15;
		gbc_token33.gridy = 3;
		getContentPane().add(token33, gbc_token33);
		
		JLabel token43 = new JLabel("");
		//token43.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token43 = new GridBagConstraints();
		gbc_token43.insets = new Insets(0, 0, 5, 5);
		gbc_token43.gridx = 20;
		gbc_token43.gridy = 3;
		getContentPane().add(token43, gbc_token43);
		
		JLabel token53 = new JLabel("");
		//token53.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token53 = new GridBagConstraints();
		gbc_token53.insets = new Insets(0, 0, 5, 5);
		gbc_token53.gridx = 25;
		gbc_token53.gridy = 3;
		getContentPane().add(token53, gbc_token53);
		
		JLabel token14 = new JLabel("");
		//token14.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token14 = new GridBagConstraints();
		gbc_token14.insets = new Insets(0, 0, 5, 5);
		gbc_token14.gridx = 5;
		gbc_token14.gridy = 4;
		getContentPane().add(token14, gbc_token14);
		
		JLabel token24 = new JLabel("");
		//token24.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token24 = new GridBagConstraints();
		gbc_token24.insets = new Insets(0, 0, 5, 5);
		gbc_token24.gridx = 10;
		gbc_token24.gridy = 4;
		getContentPane().add(token24, gbc_token24);
		
		JLabel token34 = new JLabel("");
		//token34.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token34 = new GridBagConstraints();
		gbc_token34.insets = new Insets(0, 0, 5, 5);
		gbc_token34.gridx = 15;
		gbc_token34.gridy = 4;
		getContentPane().add(token34, gbc_token34);
		
		JLabel token44 = new JLabel("");
		//token44.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token44 = new GridBagConstraints();
		gbc_token44.insets = new Insets(0, 0, 5, 5);
		gbc_token44.gridx = 20;
		gbc_token44.gridy = 4;
		getContentPane().add(token44, gbc_token44);
		
		JLabel token54 = new JLabel("");
		//token54.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token54 = new GridBagConstraints();
		gbc_token54.insets = new Insets(0, 0, 5, 5);
		gbc_token54.gridx = 25;
		gbc_token54.gridy = 4;
		getContentPane().add(token54, gbc_token54);
		
		JLabel token15 = new JLabel("");
		token15.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token15 = new GridBagConstraints();
		gbc_token15.insets = new Insets(0, 0, 5, 5);
		gbc_token15.gridx = 5;
		gbc_token15.gridy = 5;
		getContentPane().add(token15, gbc_token15);
		
		JLabel token25 = new JLabel("");
		token25.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token25 = new GridBagConstraints();
		gbc_token25.insets = new Insets(0, 0, 5, 5);
		gbc_token25.gridx = 10;
		gbc_token25.gridy = 5;
		getContentPane().add(token25, gbc_token25);
		
		JLabel token35 = new JLabel("");
		token35.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token35 = new GridBagConstraints();
		gbc_token35.insets = new Insets(0, 0, 5, 5);
		gbc_token35.gridx = 15;
		gbc_token35.gridy = 5;
		getContentPane().add(token35, gbc_token35);
		
		JLabel token45 = new JLabel("");
		token45.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token45 = new GridBagConstraints();
		gbc_token45.insets = new Insets(0, 0, 5, 5);
		gbc_token45.gridx = 20;
		gbc_token45.gridy = 5;
		getContentPane().add(token45, gbc_token45);
		
		JLabel token55 = new JLabel("");
		token55.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token55 = new GridBagConstraints();
		gbc_token55.insets = new Insets(0, 0, 5, 5);
		gbc_token55.gridx = 25;
		gbc_token55.gridy = 5;
		getContentPane().add(token55, gbc_token55);
		
		JRadioButton name1 = new JRadioButton(Config.PLAYER_NAME);
		GridBagConstraints gbc_name1 = new GridBagConstraints();
		gbc_name1.gridwidth = 2;
		gbc_name1.insets = new Insets(0, 0, 5, 5);
		gbc_name1.gridx = 7;
		gbc_name1.gridy = 6;
		getContentPane().add(name1, gbc_name1);
		
		JRadioButton name2 = new JRadioButton(Config.PLAYER_NAME);
		GridBagConstraints gbc_name2 = new GridBagConstraints();
		gbc_name2.gridwidth = 2;
		gbc_name2.insets = new Insets(0, 0, 5, 5);
		gbc_name2.gridx = 12;
		gbc_name2.gridy = 6;
		getContentPane().add(name2, gbc_name2);
		
		JRadioButton name3 = new JRadioButton(Config.PLAYER_NAME);
		GridBagConstraints gbc_name3 = new GridBagConstraints();
		gbc_name3.gridwidth = 2;
		gbc_name3.insets = new Insets(0, 0, 5, 5);
		gbc_name3.gridx = 17;
		gbc_name3.gridy = 6;
		getContentPane().add(name3, gbc_name3);
		
		JRadioButton name4 = new JRadioButton(Config.PLAYER_NAME);
		GridBagConstraints gbc_name4 = new GridBagConstraints();
		gbc_name4.gridwidth = 2;
		gbc_name4.insets = new Insets(0, 0, 5, 5);
		gbc_name4.gridx = 22;
		gbc_name4.gridy = 6;
		getContentPane().add(name4, gbc_name4);
		
		JRadioButton name5 = new JRadioButton(Config.PLAYER_NAME);
		GridBagConstraints gbc_name5 = new GridBagConstraints();
		gbc_name5.gridwidth = 2;
		gbc_name5.insets = new Insets(0, 0, 5, 5);
		gbc_name5.gridx = 27;
		gbc_name5.gridy = 6;
		getContentPane().add(name5, gbc_name5);
		
		JLabel Deck = new JLabel("");
		Deck.setIcon(ResourceLoader.loadImage(Config.IMG_IVANHOE));
		GridBagConstraints gbc_Deck = new GridBagConstraints();
		gbc_Deck.gridheight = 4;
		gbc_Deck.gridwidth = 2;
		gbc_Deck.insets = new Insets(0, 0, 5, 5);
		gbc_Deck.gridx = 21;
		gbc_Deck.gridy = 10;
		getContentPane().add(Deck, gbc_Deck);
		
		JButton withdrawButton = new JButton("Withdraw");
		GridBagConstraints gbc_withdrawButton = new GridBagConstraints();
		gbc_withdrawButton.gridheight = 3;
		gbc_withdrawButton.gridwidth = 9;
		gbc_withdrawButton.insets = new Insets(0, 0, 5, 5);
		gbc_withdrawButton.gridx = 4;
		gbc_withdrawButton.gridy = 11;
		getContentPane().add(withdrawButton, gbc_withdrawButton);

		
/*********************************************************************************/
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setMaximumSize(new java.awt.Dimension(200,200));
		scrollPane.setMinimumSize(new java.awt.Dimension(200, 200));
		scrollPane.setPreferredSize(new java.awt.Dimension(200, 200));
		
				GridBagConstraints gbc_scrollPane = new GridBagConstraints();
				gbc_scrollPane.gridwidth = 7;
				gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
				gbc_scrollPane.gridx = 12;
				gbc_scrollPane.gridy = 12;
				getContentPane().add(scrollPane, gbc_scrollPane);
				
				JTextArea displayText = new JTextArea();
				displayText.setTabSize(0);
				displayText.setRows(5);
				displayText.setColumns(5);
				displayText.setLineWrap(true);
				scrollPane.setViewportView(displayText);
		
		String temp;
		for(int i = 1; i < 100; i++){
			temp = Integer.toString(i) + "\n";
			displayText.append(temp);
		}
		
/*********************************************************************************/
		JButton playCardButton = new JButton("Play Card");
		GridBagConstraints gbc_playCardButton = new GridBagConstraints();
		gbc_playCardButton.gridheight = 3;
		gbc_playCardButton.gridwidth = 4;
		gbc_playCardButton.insets = new Insets(0, 0, 5, 5);
		gbc_playCardButton.gridx = 32;
		gbc_playCardButton.gridy = 11;
		getContentPane().add(playCardButton, gbc_playCardButton);
		
		JButton endTurnButton = new JButton("End Turn");
		GridBagConstraints gbc_endTurnButton = new GridBagConstraints();
		gbc_endTurnButton.gridheight = 3;
		gbc_endTurnButton.gridwidth = 5;
		gbc_endTurnButton.insets = new Insets(0, 0, 5, 5);
		gbc_endTurnButton.gridx = 25;
		gbc_endTurnButton.gridy = 11;
		getContentPane().add(endTurnButton, gbc_endTurnButton);
		

		
		JButton leftArrow = new JButton("");
		leftArrow.setIcon(ResourceLoader.loadImage(Config.ARROW_LEFT));
		GridBagConstraints gbc_leftArrow = new GridBagConstraints();
		gbc_leftArrow.gridheight = 3;
		gbc_leftArrow.gridwidth = 2;
		gbc_leftArrow.insets = new Insets(0, 0, 5, 5);
		gbc_leftArrow.gridx = 2;
		gbc_leftArrow.gridy = 16;
		getContentPane().add(leftArrow, gbc_leftArrow);
		
		JButton card1 = new JButton("");
		card1.setIcon(ResourceLoader.loadImage(Config.IMG_IVANHOE));
		GridBagConstraints gbc_card1 = new GridBagConstraints();
		gbc_card1.gridheight = 3;
		gbc_card1.gridwidth = 3;
		gbc_card1.insets = new Insets(0, 0, 5, 5);
		gbc_card1.gridx = 4;
		gbc_card1.gridy = 16;
		getContentPane().add(card1, gbc_card1);
		
		JButton card2 = new JButton("");
		card2.setIcon(ResourceLoader.loadImage(Config.IMG_IVANHOE));
		GridBagConstraints gbc_card2 = new GridBagConstraints();
		gbc_card2.gridheight = 3;
		gbc_card2.gridwidth = 3;
		gbc_card2.insets = new Insets(0, 0, 5, 5);
		gbc_card2.gridx = 7;
		gbc_card2.gridy = 16;
		getContentPane().add(card2, gbc_card2);
		
		JButton card3 = new JButton("");
		card3.setIcon(ResourceLoader.loadImage(Config.IMG_IVANHOE));
		GridBagConstraints gbc_card3 = new GridBagConstraints();
		gbc_card3.gridheight = 3;
		gbc_card3.gridwidth = 3;
		gbc_card3.insets = new Insets(0, 0, 5, 5);
		gbc_card3.gridx = 10;
		gbc_card3.gridy = 16;
		getContentPane().add(card3, gbc_card3);
		
		JButton card4 = new JButton("");
		card4.setIcon(ResourceLoader.loadImage(Config.IMG_IVANHOE));
		GridBagConstraints gbc_card4 = new GridBagConstraints();
		gbc_card4.gridheight = 3;
		gbc_card4.gridwidth = 3;
		gbc_card4.insets = new Insets(0, 0, 5, 5);
		gbc_card4.gridx = 13;
		gbc_card4.gridy = 16;
		getContentPane().add(card4, gbc_card4);
		
		JButton card5 = new JButton("");
		card5.setIcon(ResourceLoader.loadImage(Config.IMG_IVANHOE));
		GridBagConstraints gbc_card5 = new GridBagConstraints();
		gbc_card5.gridheight = 3;
		gbc_card5.gridwidth = 3;
		gbc_card5.insets = new Insets(0, 0, 5, 5);
		gbc_card5.gridx = 16;
		gbc_card5.gridy = 16;
		getContentPane().add(card5, gbc_card5);
		
		JButton card6 = new JButton("");
		card6.setIcon(ResourceLoader.loadImage(Config.IMG_IVANHOE));
		GridBagConstraints gbc_card6 = new GridBagConstraints();
		gbc_card6.gridheight = 3;
		gbc_card6.gridwidth = 3;
		gbc_card6.insets = new Insets(0, 0, 5, 5);
		gbc_card6.gridx = 19;
		gbc_card6.gridy = 16;
		getContentPane().add(card6, gbc_card6);
		
		JButton card7 = new JButton("");
		card7.setIcon(ResourceLoader.loadImage(Config.IMG_IVANHOE));
		GridBagConstraints gbc_card7 = new GridBagConstraints();
		gbc_card7.gridheight = 3;
		gbc_card7.gridwidth = 2;
		gbc_card7.insets = new Insets(0, 0, 5, 5);
		gbc_card7.gridx = 22;
		gbc_card7.gridy = 16;
		getContentPane().add(card7, gbc_card7);
		
		JButton card8 = new JButton("");
		card8.setIcon(ResourceLoader.loadImage(Config.IMG_IVANHOE));
		GridBagConstraints gbc_card8 = new GridBagConstraints();
		gbc_card8.gridwidth = 3;
		gbc_card8.gridheight = 3;
		gbc_card8.insets = new Insets(0, 0, 5, 5);
		gbc_card8.gridx = 24;
		gbc_card8.gridy = 16;
		getContentPane().add(card8, gbc_card8);
		
		JButton card9 = new JButton("");
		card9.setIcon(ResourceLoader.loadImage(Config.IMG_IVANHOE));
		GridBagConstraints gbc_card9 = new GridBagConstraints();
		gbc_card9.gridheight = 3;
		gbc_card9.gridwidth = 3;
		gbc_card9.insets = new Insets(0, 0, 5, 5);
		gbc_card9.gridx = 27;
		gbc_card9.gridy = 16;
		getContentPane().add(card9, gbc_card9);
		
		JButton card10 = new JButton("");
		GridBagConstraints gbc_card10 = new GridBagConstraints();
		gbc_card10.gridheight = 3;
		gbc_card10.gridwidth = 3;
		gbc_card10.insets = new Insets(0, 0, 5, 5);
		gbc_card10.gridx = 30;
		gbc_card10.gridy = 16;
		getContentPane().add(card10, gbc_card10);
		card10.setIcon(ResourceLoader.loadImage(Config.IMG_IVANHOE));
		
		JButton rightArrow = new JButton("");
		rightArrow.setIcon(ResourceLoader.loadImage(Config.ARROW_RIGHT));
		GridBagConstraints gbc_rightArrow = new GridBagConstraints();
		gbc_rightArrow.gridheight = 3;
		gbc_rightArrow.gridwidth = 2;
		gbc_rightArrow.insets = new Insets(0, 0, 5, 5);
		gbc_rightArrow.gridx = 33;
		gbc_rightArrow.gridy = 16;
		getContentPane().add(rightArrow, gbc_rightArrow);
		
		JLabel cardText = new JLabel("card text");
		GridBagConstraints gbc_cardText = new GridBagConstraints();
		gbc_cardText.gridheight = 2;
		gbc_cardText.gridwidth = 31;
		gbc_cardText.insets = new Insets(0, 0, 0, 5);
		gbc_cardText.gridx = 4;
		gbc_cardText.gridy = 19;
		getContentPane().add(cardText, gbc_cardText);
		
		JLabel points1 = new JLabel("0");
		GridBagConstraints gbc_points1 = new GridBagConstraints();
		gbc_points1.gridwidth = 2;
		gbc_points1.insets = new Insets(0, 0, 5, 5);
		gbc_points1.gridx = 7;
		gbc_points1.gridy = 7;
		getContentPane().add(points1, gbc_points1);
		
		JLabel points2 = new JLabel("0");
		GridBagConstraints gbc_points2 = new GridBagConstraints();
		gbc_points2.gridwidth = 2;
		gbc_points2.insets = new Insets(0, 0, 5, 5);
		gbc_points2.gridx = 12;
		gbc_points2.gridy = 7;
		getContentPane().add(points2, gbc_points2);
		
		JLabel points3 = new JLabel("0");
		GridBagConstraints gbc_points3 = new GridBagConstraints();
		gbc_points3.gridwidth = 2;
		gbc_points3.insets = new Insets(0, 0, 5, 5);
		gbc_points3.gridx = 17;
		gbc_points3.gridy = 7;
		getContentPane().add(points3, gbc_points3);
		
		JLabel points4 = new JLabel("0");
		GridBagConstraints gbc_points4 = new GridBagConstraints();
		gbc_points4.gridwidth = 2;
		gbc_points4.insets = new Insets(0, 0, 5, 5);
		gbc_points4.gridx = 22;
		gbc_points4.gridy = 7;
		getContentPane().add(points4, gbc_points4);
		
		JLabel points5 = new JLabel("0");
		GridBagConstraints gbc_points5 = new GridBagConstraints();
		gbc_points5.gridwidth = 2;
		gbc_points5.insets = new Insets(0, 0, 5, 5);
		gbc_points5.gridx = 27;
		gbc_points5.gridy = 7;
		getContentPane().add(points5, gbc_points5);
		
		//naming the buttons 
		/*this.withdrawButton = withdrawButton;
		this.deck = Deck;
		this.leftArrow = leftArrow;
		this.rightArrow = rightArrow;
		this.playCardButton = playCardButton;
		this.endTurnButton = endTurnButton;
		this.textLabel = textLabel;
		this.cardTextLabel = cardText;
		
		this.playerCards= new JButton[10];
		this.playerCards[0] = card1;
		this.playerCards[1] = card2;
		this.playerCards[2] = card3;
		this.playerCards[3] = card4;
		this.playerCards[4] = card5;
		this.playerCards[5] = card6;
		this.playerCards[6] = card7;
		this.playerCards[7] = card8;
		this.playerCards[8] = card9;
		this.playerCards[9] = card10;
		
		this.tokens = new JLabel[5][5];
		this.tokens[0][0] = token11;
		this.tokens[0][1] = token12;
		this.tokens[0][2] = token13;
		this.tokens[0][3] = token14;
		this.tokens[0][4] = token15;
		
		this.tokens[1][0] = token21;
		this.tokens[1][1] = token22;
		this.tokens[1][2] = token23;
		this.tokens[1][3] = token24;
		this.tokens[1][4] = token25;
		
		this.tokens[2][0] = token31;
		this.tokens[2][1] = token32;
		this.tokens[2][2] = token33;
		this.tokens[2][3] = token34;
		this.tokens[2][4] = token35;
		
		this.tokens[3][0] = token41;
		this.tokens[3][1] = token42;
		this.tokens[3][2] = token43;
		this.tokens[3][3] = token44;
		this.tokens[3][4] = token45;
		
		this.tokens[4][0] = token51;
		this.tokens[4][1] = token52;
		this.tokens[4][2] = token53;
		this.tokens[4][3] = token54;
		this.tokens[4][4] = token55;
		
		
		this.playerNames = new JRadioButton[5];
		this.playerNames[0] = name1;
		this.playerNames[1] = name2;
		this.playerNames[2] = name3;
		this.playerNames[3] = name4;
		this.playerNames[4] = name5;
		for(int i = 0; i < 5; i++){
			this.playerNames[i].setEnabled(false);
		}
		
		this.playedCards = new JButton[5];
		this.playedCards[0] = p1d;
		this.playedCards[1] = p2d;
		this.playedCards[2] = p3d;
		this.playedCards[3] = p4d;
		this.playedCards[4] = p5d;
		
		this.playerPoints = new JLabel[5];
		this.playerPoints[0] = points1;
		this.playerPoints[1] = points2;
		this.playerPoints[2] = points3;
		this.playerPoints[3] = points4;
		this.playerPoints[4] = points5;*/
	}

	

}

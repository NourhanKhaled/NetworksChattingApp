package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import model.Message;

@SuppressWarnings("serial")
public class ChatBoxPanel extends JPanel{

	Font f;
	JPanel tempPanel;
	JScrollPane scrollPane;

	ArrayList<Message> chatHistory;

	public ChatBoxPanel(ArrayList<Message> history) {
		
		this.chatHistory= history;
		setSize(420, 500);
		setBorder(BorderFactory.createEmptyBorder());
		setOpaque(false);
		

		// INSIDE CHATBOX

		JLabel tempPanel = new JLabel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Dimension arcs = new Dimension(15,15);
				int width = getWidth();
				int height = getHeight();
				Graphics2D graphics = (Graphics2D) g;
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				graphics.setColor(getBackground());
				graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);	//paint background
				graphics.setColor(getForeground());
				graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);	//paint border
			}
		};
		tempPanel.setLayout(new GridBagLayout());
		tempPanel.setPreferredSize(new Dimension(350, chatHistory.size()*35));
		tempPanel.setOpaque(false);
		GridBagConstraints c = new GridBagConstraints();

		Font f2 = new Font("Arial Rounded MT Bold", Font.PLAIN, 12);

		int counter = 0;
		for (Message message: chatHistory)
		{
			JLabel label;
			JButton button;
			c.gridy = counter;
			c.weightx = 0.0000001;
			c.weighty = 0.001;

			if(message.isMe()) {
				label = new JLabel(message.getMessage(), SwingConstants.RIGHT);
				label.setForeground(Color.BLUE);
				label.setFont(f2);
				button = new JButton();
				button.add(label);
				c.anchor = GridBagConstraints.EAST;
				c.insets = new Insets(0,0,0,10);
				tempPanel.add(button, c);
			}
			else  {
				label = new JLabel(message.getMessage(), SwingConstants.LEFT);
				label.setForeground(Color.BLACK);
				label.setFont(f2);
				button = new JButton();
				button.add(label);
				c.anchor = GridBagConstraints.WEST;
				c.insets = new Insets(0,10,0,0);
				tempPanel.add(button, c);
			}
			counter++;
		}


		JScrollPane scrollPane = new JScrollPane(tempPanel) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Dimension arcs = new Dimension(15,15);
				int width = getWidth();
				int height = getHeight();
				Graphics2D graphics = (Graphics2D) g;
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);
			}
		};
		scrollPane.setPreferredSize(new Dimension(420, 450));

		
		add(scrollPane, BorderLayout.CENTER);

		revalidate();
		repaint();
		setVisible(true);
	}


}
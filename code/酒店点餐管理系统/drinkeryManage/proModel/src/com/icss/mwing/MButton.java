package com.icss.mwing;

import java.awt.Insets;

import javax.swing.JButton;

public class MButton extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MButton() {
		setContentAreaFilled(false);
		setMargin(new Insets(0, 0, 0, 0));
		setBorderPainted(false);
		setFocusPainted(false);
	}
}

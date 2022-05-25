package qqchat;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Register extends JFrame{
	JPanel p = new JPanel();
	JDBC jdbc = new JDBC();
	
	int num = 0; //记录注册账号的次数，防止频繁注册
	Register(){
		
		Font font1 = new Font("", Font.BOLD, 26);
		Font font2 = new Font("", Font.PLAIN, 18);
		Font font3 = new Font("", Font.LAYOUT_LEFT_TO_RIGHT, 15);
		
		//文本框 账号
		JTextField account = new JTextField();
		account.setBounds(180, 17, 200, 40);
		account.setFont(font1);
		account.setBackground(Color.LIGHT_GRAY);
		p.add(account);
				
		//文本框 密码
		JPasswordField secret = new JPasswordField();
		secret.setBounds(180, 65, 200, 40);
		secret.setFont(font1);
		secret.setBackground(Color.LIGHT_GRAY);
		p.add(secret);
		
		//新账号输入提示
		JLabel putnumber = new JLabel("请输入你的新账号：");
		putnumber.setBounds(7, 18, 175, 35);
		putnumber.setFont(font2);
		p.add(putnumber);
		
		//新密码输入提示
		JLabel putpassword=new JLabel("请输入你的新密码：");
		putpassword.setBounds(7, 66, 175, 35);
		putpassword.setFont(font2);
		p.add(putpassword);
		
		//注册按键
		JButton btn = new JButton("注 册");
		btn.setBounds(280, 150, 80, 40);
		btn.setFont(font3);
		p.add(btn);
		
		//新账号输入框添加键盘监听，按下回车键密码输入框获得聚焦
		account.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyTyped(KeyEvent e) {
			    if (e.getKeyChar() == KeyEvent.VK_ENTER)
			    {
			    	secret.grabFocus();
			    }
			}
		});
		
		//新密码输入框添加键盘监听，按下回车键触发注册按键
		secret.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyTyped(KeyEvent e) {
			    if (e.getKeyChar() == KeyEvent.VK_ENTER)
			    {
			    	if(num >= 2) {
	    				JOptionPane.showMessageDialog(null,"频繁操作!","你个小调皮",JOptionPane.ERROR_MESSAGE); 
	    			}
			    	else {
				    	String number = account.getText();
				    	if(number.length() > 10) {
				    		JOptionPane.showMessageDialog(null,"账号长度不能大于10个字符！","DO YOURSELF",JOptionPane.WARNING_MESSAGE);
				    	}
				    	else if(number.length() < 5) {
				    		JOptionPane.showMessageDialog(null,"账号长度不能小于 5个字符！","CAN'T DO IT",JOptionPane.WARNING_MESSAGE);
				    	}
				    	else {
				    		jdbc.searchNum(number);
				    		if(jdbc.isLoginSuc()) {
				    			JOptionPane.showMessageDialog(null,"账号已存在！","SO SAD",JOptionPane.WARNING_MESSAGE);
				    		}
				    		else {
				    			String passwordnum = new String(secret.getPassword());
				    			if(passwordnum.length() > 10) {
						    		JOptionPane.showMessageDialog(null,"密码长度不能大于10个字符！","HHHHHHH",JOptionPane.WARNING_MESSAGE);
						    	}
						    	else if(passwordnum.length() < 5) {
						    		JOptionPane.showMessageDialog(null,"密码长度不能小于 5个字符！","twinkle twinkle little star",JOptionPane.WARNING_MESSAGE);
						    	}
				    			else {
				    				jdbc.insertNewAccount(number, passwordnum);
				    				JLabel sucl = new JLabel();
				    				if(jdbc.isCount()) {
				    					sucl.setText("注册成功！");
				    					++num;
				    				}
				    				else {
				    					sucl.setText("注册失败！");
				    				}
				    				sucl.setFont(font2);
			    					sucl.setBounds(280, 230, 120, 50);
			    					p.add(sucl);
			    					SwingUtilities.updateComponentTreeUI(p);
				    			}
				    		}
				    	}
			    	}
			    }
			}
		});

		btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
		    	if(num >= 2) {
	    			JOptionPane.showMessageDialog(null,"频繁操作!","你个小调皮",JOptionPane.ERROR_MESSAGE); 
	    		}
			   	else {
				   	String number = account.getText();
				   	if(number.length() > 10) {
				   		JOptionPane.showMessageDialog(null,"账号长度不能大于10个字符！","DO YOURSELF",JOptionPane.WARNING_MESSAGE);
				   	}
				    else if(number.length() < 5) {
				    	JOptionPane.showMessageDialog(null,"账号长度不能小于 5个字符！","CAN'T DO IT",JOptionPane.WARNING_MESSAGE);
			    	}
			    	else {
			    		jdbc.searchNum(number);
			    		if(jdbc.isLoginSuc()) {
				   			JOptionPane.showMessageDialog(null,"账号已存在！","SO SAD",JOptionPane.WARNING_MESSAGE);
				   		}
				   		else {
				   			String passwordnum = new String(secret.getPassword());
				    		if(passwordnum.length() > 10) {
						   		JOptionPane.showMessageDialog(null,"密码长度不能大于10个字符！","HHHHHHH",JOptionPane.WARNING_MESSAGE);
						   	}
						   	else if(passwordnum.length() < 5) {
						   		JOptionPane.showMessageDialog(null,"密码长度不能小于 5个字符！","twinkle twinkle little star",JOptionPane.WARNING_MESSAGE);
						    }
			    			else {
			    				jdbc.insertNewAccount(number, passwordnum);
			    				JLabel sucl = new JLabel();
			    				if(jdbc.isCount()) {
				   					sucl.setText("注册成功！");
				   					++num;
				   				}
				   				else {
				    				sucl.setText("注册失败！");
				    			}
				    			sucl.setFont(font2);
			    				sucl.setBounds(280, 230, 120, 50);
		    					p.add(sucl);
		    					SwingUtilities.updateComponentTreeUI(p);
			    			}
			    		}
			    	}
			    }
			}
		});
		
		JLabel tip = new JLabel("（账号密码长度为5~10个字符）");
		tip.setBounds(20,  120, 380,  30);
		tip.setForeground(Color.DARK_GRAY);
		p.add(tip);
		
		ImageIcon icon = new ImageIcon("src/logo.png");
		JLabel licon = new JLabel(icon);
		licon.setBounds(5,  150,  200,  200);
		p.add(licon);
		
		p.setLayout(null);
		p.setBounds(0, 0, 400, 400);
	
		this.add(p);
		this.setLayout(null);
		this.setName("Do you wanna 注册 a 账号?!");
		this.setSize(400, 400);
		this.setLocationRelativeTo(null);//设置窗口位置在屏幕中央
		this.setVisible(true);
	}
}

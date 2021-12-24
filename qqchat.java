package qqchat;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import javax.swing.*;


public class qqchat{
	public static void main(String[] args) {
		new chat();
	}
}

class friendChatFrame implements Runnable{
	DataInputStream in;
	DataOutputStream out;
	JTextArea text1;//输入区
	JTextArea text2;//显示区
	JFrame talkf;
	Socket socket;
	String myName;
	String name;
	JButton send;
	int i;//1代表私人聊天，2代表群聊
	public friendChatFrame(String name,String myName,int i) {///聊天框;
		try {
        	//向本机的10124端口发出客户请求
            this.socket=new Socket("127.0.0.1",10124);
            //由Socket对象得到输入流
            in=new DataInputStream(socket.getInputStream());
            //由Socket对象得到输出流
            out=new DataOutputStream(socket.getOutputStream());
           } catch (IOException e) {}
		this.i=i;
		this.name=name;
		this.myName=myName;
		talkf=new JFrame(name);
		talkf.setLayout(null);
		
		text1=new JTextArea();
		text1.setLineWrap(true);
		text1.setBounds(0,340,400,130);
		talkf.add(text1);
		
		text2=new JTextArea();
		text2.setBounds(0,0,400,310);
		text2.setOpaque(false);
		text2.setEditable(false);
		text2.setBorder(null);
		text2.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(text2);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(null);  
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
        scrollPane.setBounds(0,0,400,310);
		talkf.add(scrollPane);
		
		JPanel tool=new JPanel();
		tool.setBounds(0, 310, 400, 30);
		tool.setBackground(Color.white);
		tool.setLayout(null);
		send=new JButton("发  送");
		send.setBounds(300,0,100,30);
		send.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String ss=text1.getText();
				int i=0;
				for(;i<ss.length();++i) {
					if(ss.charAt(i)!=' ') {
						break;
					}
				}
				ss=ss.substring(i,ss.length());
					if(ss.length()>0) {
						try {
							out.writeUTF(myName+": "+ss+name);
							out.flush();
							text1.setText("");
						}catch (IOException e1) {
							e1.printStackTrace();
					}
				}
				text1.setText("");
			}
		});
		text1.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyTyped(KeyEvent e) {
		    	if (e.getKeyChar() == KeyEvent.VK_ENTER){
		    		String ss=text1.getText();
					int i=0;
					for(;i<ss.length();++i) {
						if(ss.charAt(i)!=' ') {
							break;
						}
					}
					ss=ss.substring(i,ss.length()-1);
					if(ss.length()>0) {
						try {
							out.writeUTF(myName+": "+ss+name);
							out.flush();
							text1.setText("");
						}catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					text1.setText("");
		    	}
		    }
		});
		tool.add(send);
		talkf.add(tool);
		
		talkf.setSize(400,500);
		talkf.setLocationRelativeTo(null);//设置窗口位置在屏幕中央
		talkf.setVisible(true);
	}
	@Override
	public void run() {
		try{
			while(true){
	        	String ss=in.readUTF();
	        	String ssName=ss.substring(ss.length()-name.length());
	        	String ssMyName=ss.substring(0,myName.length());
	        	if(i==2) {
		        		if(ssName.equals(name)) {
		        		ss=ss.substring(0,ss.length()-ssName.length());
			    		text2.append(ss+"\n");
			    		text2.setCaretPosition(text2.getText().length());
	        		}
	        	}
	        	else {
		        	if(ssName.equals(name)&&ssMyName.equals(myName)){
		        		ss=ss.substring(0,ss.length()-ssName.length());
			    		text2.append(ss+"\n");
			    		text2.setCaretPosition(text2.getText().length());//将滚动条自动拉到JTextArea最底端
		        	}
		        	else {
		        		ssName=ss.substring(ss.length()-myName.length());
			        	ssMyName=ss.substring(0,name.length());
			        	if(ssName.equals(myName)&&ssMyName.equals(name)) {
			        		ss=ss.substring(0,ss.length()-ssName.length());
				    		text2.append(ss+"\n");
				    		text2.setCaretPosition(text2.getText().length());
			        	}
		        	}
	        	}
	    		out.flush();
             }
         }catch(IOException e){
    		e.printStackTrace();
    	}
	}
}

class chat{
	private JFrame fw=new JFrame("Welcome to happy valley!");//welcome界面
	private JFrame frame=new JFrame("睡什么睡起来嗨！！！");//登录界面
	private JFrame fs=new JFrame("咕嘟咕嘟");//主界面
	
	private boolean remBox=true;//判断是否有记住密码
	private int remBoxi=0;
	private boolean logBox=false;//判断是否有自动登录
	private int logBoxi=-1;
	
	private String[] Saccount= {"1155665","3939339","9087456"};
	private String[] Spassword= {"abccbba","dobbie","voldemort"};
	private String[] clientName= {"PPAP","小精灵多比","伏地魔"};
	private String[] photoM= {"src/tou2.png","src/DB.png","src/voldemort.png"};
	private int[][] theFriend= {{1,2},{0,2},{0,1}};
	private String[] theGroop= {"SkyTeam"};
	private String[] theGroopP= {"src/non.png"};
	private int nowi=-1;
	
	//自己创建的wait函数
	private void myWait(int i) {
		long n=100000,m=10000;
		n*=m*i;
		while(n>0) {
			n--;
		}
	}
	
	///欢迎使用
	chat(){
		ImageIcon wel=new ImageIcon("src/wel.png");
		JLabel labw=new JLabel(wel);
		labw.setSize(wel.getIconWidth(),wel.getIconHeight());
		fw.add(labw);
		fw.setSize(wel.getIconWidth(), wel.getIconHeight());
		fw.setLocationRelativeTo(null);//设置窗口位置在屏幕中央
		fw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fw.setVisible(true);
		this.myWait(5);
		fw.dispose();
		this.qqlogin();
	}
	
	//////登录界面
	private void qqlogin() {
		//背景图
		ImageIcon bg=new ImageIcon("src/qq.jpg");
		JLabel label1=new JLabel(bg);
		int width=bg.getIconWidth();
		int height=bg.getIconHeight();
		label1.setSize(width,height);
		frame.getLayeredPane().add(label1,Integer.valueOf(Integer.MIN_VALUE));
		
		JPanel pan_background=(JPanel)frame.getContentPane();
		pan_background.setLayout(null);
		pan_background.setOpaque(false);
		//头像
		ImageIcon pho=new ImageIcon("src/ok.png");
		JLabel label2=new JLabel(pho);
		label2.setSize(pho.getIconWidth(), pho.getIconHeight());
		label2.setBounds(10,10,60,60);
		pan_background.add(label2);
		//文本框 账号
		JTextField account=new JTextField();
		account.setBounds(75,17,100,20);
		pan_background.add(account);
		//文本框 密码
		JPasswordField secret=new JPasswordField();
		secret.setBounds(75,43,100,20);
		pan_background.add(secret);
		//账号输入框添加回车键监听，若按下回车键则让密码输入框获取焦点
		account.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyTyped(KeyEvent e) {
		    if (e.getKeyChar() == KeyEvent.VK_ENTER)
		    {
		    	secret.grabFocus();
		    }
		}
		});
		//密码输入框回车键触发登录事件
		secret.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyTyped(KeyEvent e) {
		    if (e.getKeyChar() == KeyEvent.VK_ENTER)
		    {
		    	int flag=0;
				for(int i=0;i<Saccount.length;++i) {
					if(account.getText().equals(Saccount[i])) {
						flag=1;
						if(new String(secret.getPassword()).equals(Spassword[i])) {
							if(remBox==true) {
								remBoxi=i;
							}
							else remBoxi=-1;
							if(logBox==true) {
								logBoxi=i;
							}
							else logBoxi=-1;
							nowi=i;
							chat.this.listpage1(i);
						}
						else {
							JOptionPane.showMessageDialog(null,"密码错误!","OH NO!!!",JOptionPane.ERROR_MESSAGE); 
						}
						break;
					}
				}
				if(flag==0) {
					JOptionPane.showMessageDialog(null,"账号不存在!","OH MY GOD!!!",JOptionPane.WARNING_MESSAGE);
				}
		    }
		}
		});

		//自动登录复选框
		JCheckBox alogin=new JCheckBox("自动登录",logBox);
		alogin.setBounds(10,70,90,13);
		alogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logBox=!logBox;
			}
		});
		pan_background.add(alogin);
		if(remBox==true) {
			account.setText(Saccount[remBoxi]);
			secret.setText(Spassword[remBoxi]);
		}
		if(logBox==true) {
			this.listpage1(logBoxi);
		}
		//记住密码复选框
		JCheckBox rem=new JCheckBox("记住密码",remBox);
		rem.setBounds(95,70,90,13);
		rem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remBox=!remBox;
				remBoxi=-1;
			}
		});
		pan_background.add(rem);
		
		//注册账号,找回密码
		JLabel register=new JLabel("注册账号");
		register.setBounds(183, 15, 80, 20);
		pan_background.add(register);
		
		JLabel fsecret=new JLabel("找回密码");
		fsecret.setBounds(183, 41, 80, 20);
		pan_background.add(fsecret);
		
		//登录按钮
		JButton loginb=new JButton("登  录");
		loginb.setBounds(40, 94, 60, 20);
		pan_background.add(loginb);
		//登录事件触发
		loginb.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int flag=0;
				for(int i=0;i<Saccount.length;++i) {
					if(account.getText().equals(Saccount[i])) {
						flag=1;
						if(new String(secret.getPassword()).equals(Spassword[i])) {
							if(remBox==true) {
								remBoxi=i;
							}
							else remBoxi=-1;
							if(logBox==true) {
								logBoxi=i;
							}
							else logBoxi=-1;
							nowi=i;
							chat.this.listpage1(i);
						}
						else {
							JOptionPane.showMessageDialog(null,"密码错误!","OH NO!!!",JOptionPane.ERROR_MESSAGE); 
						}
						break;
					}
				}
				if(flag==0) {
					JOptionPane.showMessageDialog(null,"账号不存在!","OH MY GOD!!!",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		frame.setSize(width,height);
		frame.setLocationRelativeTo(null);//设置窗口位置在屏幕中央
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	////聊天列表界面
	///关闭时退出
	private JPanel pan_me=(JPanel)fs.getContentPane();
	///界面右下部分按钮
	private JButton bnt_talk;
	private JButton bnt_friend;
	private JButton bnt_dyn;
	private JButton bnt_search;
	private JButton bnt_ext;
	private void listpage1(int i){
		frame.dispose();
		ImageIcon tou=new ImageIcon(photoM[i]);
		JLabel labt=new JLabel(tou);
		labt.setBounds(10,8,tou.getIconWidth(),tou.getIconHeight());
		
		pan_me.setLayout(null);
		pan_me.setOpaque(false);
		pan_me.add(labt);

		//本人资料
		JLabel name=new JLabel("昵称："+clientName[i]);
		name.setBounds(80,8,160,14);
		pan_me.add(name);
		
		JLabel sex=new JLabel("性别：你猜！");
		sex.setBounds(80,29,160,14);
		pan_me.add(sex);
		
		JLabel num=new JLabel("账号："+Saccount[i]);
		num.setBounds(80,50,160,14);
		num.setOpaque(false);
		pan_me.add(num);
		
		//按键字体
		Font bf=new Font("",Font.PLAIN,13);
		///设置按钮
		ImageIcon setm=new ImageIcon("src/set.png");
		JLabel set=new JLabel(setm);
		set.setBounds(303, 5,30,30);
		pan_me.add(set);
		
		//聊天
		ImageIcon talk=new ImageIcon("src/聊天.png");
		bnt_talk=new JButton("聊天",talk);
		bnt_talk.setBounds(283,220,50,65);
		bnt_talk.setBorderPainted(false);//去掉边框
		bnt_talk.setVerticalTextPosition(JButton.BOTTOM);
		bnt_talk.setHorizontalTextPosition(JButton.CENTER);
		bnt_talk.setForeground(Color.GRAY);
		bnt_talk.setFont(bf);
		pan_me.add(bnt_talk);
		
		//好友
		ImageIcon friend=new ImageIcon("src/好友.png");
		bnt_friend=new JButton("好友",friend);
		bnt_friend.setBounds(283,300,50,60);
		bnt_friend.setBorderPainted(false);
		bnt_friend.setVerticalTextPosition(JButton.BOTTOM);
		bnt_friend.setHorizontalTextPosition(JButton.CENTER);
		bnt_friend.setForeground(Color.GRAY);
		bnt_friend.setFont(bf);
		pan_me.add(bnt_friend);
		
		//动态
		ImageIcon dyn=new ImageIcon("src/动态.png");
		bnt_dyn=new JButton("动态",dyn);
		bnt_dyn.setBounds(283,375,50,60);
		bnt_dyn.setBorderPainted(false);
		bnt_dyn.setVerticalTextPosition(JButton.BOTTOM);
		bnt_dyn.setHorizontalTextPosition(JButton.CENTER);
		bnt_dyn.setForeground(Color.GRAY);
		bnt_dyn.setFont(bf);
		pan_me.add(bnt_dyn);
				
		//搜索
		ImageIcon search=new ImageIcon("src/搜索.png");
		bnt_search=new JButton("搜索",search);
		bnt_search.setBounds(283,450,50,60);
		bnt_search.setBorderPainted(false);
		bnt_search.setVerticalTextPosition(JButton.BOTTOM);
		bnt_search.setHorizontalTextPosition(JButton.CENTER);
		bnt_search.setForeground(Color.GRAY);
		bnt_search.setFont(bf);
		pan_me.add(bnt_search);
		
		//退出
		ImageIcon back=new ImageIcon("src/返回.png");
		bnt_ext=new JButton("退出",back);
		bnt_ext.setBounds(283,525,50,60);
		bnt_ext.setBorderPainted(false);
		bnt_ext.setOpaque(false);
		bnt_ext.setVerticalTextPosition(JButton.BOTTOM);
		bnt_ext.setHorizontalTextPosition(JButton.CENTER);
		bnt_ext.setForeground(Color.GRAY);
		bnt_ext.setFont(bf);
		pan_me.add(bnt_ext);
		
		

		pan_me.add(mainp);
		mainp.setBounds(0,80,280,505);
		mainp.setLayout(null);
		mainp.setVisible(true);
		bnt_talk.setForeground(Color.LIGHT_GRAY);
		talkPane();
		
		fs.setSize(340,620);
		fs.setLayout(null);
		pan_me.setBounds(0,0,340,650);
		fs.setLocationRelativeTo(null);//设置窗口位置在屏幕中央
		fs.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fs.setVisible(true);
		chat.this.actionPerformed(null);
	}

	private JPanel mainp=new JPanel();
	
 	private void talkPane() {

		//滚动条
		JScrollPane sp=new JScrollPane();
		sp.setBounds(-1,-1,285,510);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		sp.setBorder(BorderFactory.createEtchedBorder());
				
		JPanel mainp2=new JPanel();
		mainp2.setPreferredSize(new Dimension(256,10*68));
		mainp2.setVisible(true);
		mainp2.setLayout(null);
		mainp2.setOpaque(true);
		mainp2.setBackground(Color.white);
				
		sp.getViewport().add(mainp2);
		sp.validate();
		mainp.add(sp);
						
		//名字字体
		Font namef=new Font("",Font.BOLD,14);
		//时间字体
		Font timef=new Font("",Font.BOLD,10);
		//聊天列表
		JPanel ChatFriend=new JPanel();
		ChatFriend.setVisible(true);
		ChatFriend.setLayout(null);
		ChatFriend.setOpaque(true);
		ChatFriend.setBackground(Color.white);
		ChatFriend.setBounds(0, 0, 256, 60);
		ImageIcon ct1=new ImageIcon(photoM[theFriend[nowi][0]]);
		JLabel ft1=new JLabel(ct1);
		JLabel fn1=new JLabel(clientName[theFriend[nowi][0]]);
		fn1.setFont(namef);
		JLabel fc1=new JLabel("Dobbie is free! ");
		JLabel ftime1=new JLabel("14:57");
		ft1.setBounds(10,6,55,55);
		ChatFriend.add(ft1);
		fn1.setBounds(85, 5, 115, 20);
		ChatFriend.add(fn1);
		fc1.setBounds(85, 40, 185, 20);
		ChatFriend.add(fc1);
		ftime1.setBounds(210,5,40,15);
		ftime1.setForeground(Color.GRAY);
		ftime1.setFont(timef);
		ChatFriend.add(ftime1);
		ChatFriend.addMouseListener(new ChatFrame(fn1.getText(),clientName[nowi],1));
		
		JPanel ChatFriend2=new JPanel();
		ChatFriend2.setVisible(true);
		ChatFriend2.setLayout(null);
		ChatFriend2.setOpaque(true);
		ChatFriend2.setBackground(Color.white);
		ChatFriend2.setBounds(0, 60, 256, 60);
		ImageIcon ct2=new ImageIcon(theGroopP[0]);
		JLabel ft2=new JLabel(ct2);
		JLabel fn2=new JLabel(theGroop[0]);
		fn2.setFont(namef);
		JLabel ftime2=new JLabel("00:06");
		ft2.setBounds(10,6,55,55);
		ChatFriend2.add(ft2);
		fn2.setBounds(85, 5, 115, 20);
		ChatFriend2.add(fn2);
		ftime2.setBounds(210,5,40,15);
		ftime2.setForeground(Color.GRAY);
		ftime2.setFont(timef);
		ChatFriend2.add(ftime2);
		ChatFriend2.addMouseListener(new ChatFrame(fn2.getText(),clientName[nowi],2));
		
		mainp2.add(ChatFriend);
		mainp2.add(ChatFriend2);	
	}

 	private JPanel Friend;
	private void friendPane() {
		
		
		JScrollPane sp=new JScrollPane();
		sp.setBounds(-1,-1,285,510);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		sp.setBorder(BorderFactory.createEtchedBorder());
		
		Friend=new JPanel();
		Friend.setPreferredSize(new Dimension(256,10*68));
		Friend.setVisible(true);
		Friend.setLayout(null);
		Friend.setOpaque(true);
		Friend.setBackground(Color.white);
		sp.getViewport().add(Friend);
		sp.validate();
		mainp.add(sp);
		
		Font namef=new Font("",Font.PLAIN,14);
		Font ztf=new Font("",Font.PLAIN,12);
		
		JPanel pf1=new JPanel();
		pf1.setVisible(true);
		pf1.setLayout(null);
		pf1.setOpaque(true);
		pf1.setBackground(Color.white);
		pf1.setBounds(0, 0, 256, 60);
		ImageIcon ct1=new ImageIcon(photoM[theFriend[nowi][0]]);
		JLabel ft1=new JLabel(ct1);
		JLabel fn1=new JLabel(clientName[theFriend[nowi][0]]);
		JLabel fz1=new JLabel("[在线]");
		ft1.setBounds(10, 6, 55, 55);
		fn1.setBounds(85, 0, 160, 30);
		fz1.setBounds(85, 40, 50, 20);
		fz1.setForeground(Color.DARK_GRAY);
		fn1.setFont(namef);
		fz1.setFont(ztf);
		pf1.add(ft1);
		pf1.add(fn1);
		pf1.add(fz1);
		pf1.addMouseListener(new ChatFrame(fn1.getText(),clientName[nowi],1));
		
		JPanel pf2=new JPanel();
		pf2.setVisible(true);
		pf2.setLayout(null);
		pf2.setOpaque(true);
		pf2.setBackground(Color.white);
		pf2.setBounds(0, 60, 256, 60);
		ImageIcon ct2=new ImageIcon(photoM[theFriend[nowi][1]]);
		JLabel ft2=new JLabel(ct2);
		JLabel fn2=new JLabel(clientName[theFriend[nowi][1]]);
		JLabel fz2=new JLabel("[在线]");
		ft2.setBounds(10, 6, 55, 55);
		fn2.setBounds(85, 0, 160, 30);
		fz2.setBounds(85, 40, 50, 20);
		fz2.setForeground(Color.DARK_GRAY);
		fn2.setFont(namef);
		fz2.setFont(ztf);
		pf2.add(ft2);
		pf2.add(fn2);
		pf2.add(fz2);
		pf2.addMouseListener(new ChatFrame(fn2.getText(),clientName[nowi],1));
		
		Friend.add(pf1);
		Friend.add(pf2);
	}
	
	private void roomPane() {
		
		JScrollPane sp=new JScrollPane();
		sp.setBounds(-1,-1,285,510);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		sp.setBorder(BorderFactory.createEtchedBorder());
		
		JPanel mainp2=new JPanel();
		mainp2.setPreferredSize(new Dimension(256,260*3+5));
		mainp2.setVisible(true);
		mainp2.setLayout(null);
		mainp2.setOpaque(false);
		sp.getViewport().add(mainp2);
		sp.validate();
		mainp.add(sp);
		
		Font namef=new Font("",Font.BOLD,14);
		
		JPanel dt1=new JPanel();
		dt1.setVisible(true);
		dt1.setLayout(null);
		dt1.setOpaque(true);
		dt1.setBackground(Color.white);
		dt1.setBounds(5, 5, 255, 255);
		dt1.setBorder(BorderFactory.createTitledBorder("13:45"));
		ImageIcon ct1=new ImageIcon("src/DB.png");
		JLabel ft1=new JLabel(ct1);
		JLabel fn1=new JLabel("小精灵多比");
		ft1.setBounds(190, 9, 55, 55);
		fn1.setBounds(0, 8, 170, 30);
		fn1.setFont(namef);
		fn1.setHorizontalAlignment(SwingConstants.RIGHT);
		dt1.add(ft1);
		dt1.add(fn1);
		///
		JLabel text1=new JLabel("多比自由了！！！！");
		text1.setBounds(15, 75, 225, 30);
		dt1.add(text1);
		ImageIcon tp1=new ImageIcon("src/dbfree.png");
		JLabel tpl1=new JLabel(tp1);
		tpl1.setBounds(0, 100, 250, 150);
		dt1.add(tpl1);
		
		
		JPanel dt2=new JPanel();
		dt2.setVisible(true);
		dt2.setLayout(null);
		dt2.setOpaque(true);
		dt2.setBackground(Color.white);
		dt2.setBounds(5, 265, 255, 255);
		dt2.setBorder(BorderFactory.createTitledBorder(" 9:12"));
		ImageIcon ct2=new ImageIcon("src/tutu.png");
		JLabel ft2=new JLabel(ct2);
		JLabel fn2=new JLabel("胡图图");
		ft2.setBounds(190, 9, 55, 55);
		fn2.setBounds(0, 8, 170, 30);
		fn2.setFont(namef);
		fn2.setHorizontalAlignment(SwingConstants.RIGHT);
		dt2.add(ft2);
		dt2.add(fn2);
		///
		String t2str="<html>我叫胡图图<br/>我的爸爸叫胡英俊"
				+ "<br/>我的妈妈叫张小丽<br/>我家住在翻斗花园二号楼一零零一室"
				+ "<br/>妈妈做的炸小肉丸最好吃<br/>我的猫咪叫小怪"
				+ "<br/>他是一只会说话的猫咪哟<br/>小怪和图图一样是个男孩子</html>";
		JLabel text2=new JLabel(t2str);
		text2.setBounds(15, 75, 225, 175);
		dt2.add(text2);
		
		
		JPanel dt3=new JPanel();
		dt3.setVisible(true);
		dt3.setLayout(null);
		dt3.setOpaque(true);
		dt3.setBackground(Color.white);
		dt3.setBounds(5, 525, 255, 255);
		dt3.setBorder(BorderFactory.createTitledBorder(" 5:21"));
		ImageIcon ct3=new ImageIcon("src/tou2.png");
		JLabel ft3=new JLabel(ct3);
		JLabel fn3=new JLabel("PPAP");
		ft3.setBounds(190, 9, 55, 55);
		fn3.setBounds(0, 8, 170, 30);
		fn3.setFont(namef);
		fn3.setHorizontalAlignment(SwingConstants.RIGHT);
		dt3.add(ft3);
		dt3.add(fn3);
		///
		JLabel text3=new JLabel("美好的一天开始啦！");
		text3.setBounds(15, 75, 225, 30);
		dt3.add(text3);
		ImageIcon tp3=new ImageIcon("src/dtt3.png");
		JLabel tpl3=new JLabel(tp3);
		tpl3.setBounds(0, 100, 250, 150);
		dt3.add(tpl3);
		
		
		
		mainp2.add(dt1);
		mainp2.add(dt2);
		mainp2.add(dt3);
	}
	
	private void searchPane() {
		
		JTextField searchtf=new JTextField("");
		searchtf.setBounds(25,10,200,20);
		JButton searchll=new JButton("搜索");
		searchll.setBounds(227, 5, 50, 30);
		searchll.setForeground(Color.gray);
		mainp.add(searchll);
		mainp.add(searchtf);
		
		//添加好友
		ImageIcon addFriend=new ImageIcon("src/addF2.png");
		JButton addF=new JButton(addFriend);
		addF.setBounds(25,40,55,65);
		addF.setBorderPainted(false);
		mainp.add(addF);
	}
	
	private void buttonUpdate() {
		bnt_talk.setForeground(Color.GRAY);
		bnt_friend.setForeground(Color.GRAY);
		bnt_dyn.setForeground(Color.GRAY);
		bnt_search.setForeground(Color.GRAY);
	}
	
	class ChatFrame implements MouseListener{
		String s,s1;
		int i;
		ChatFrame(String s,String s1,int i){
			this.s=s;
			this.s1=s1;
			this.i=i;
		}
		@Override
		public void mouseReleased(MouseEvent e) {
		}
		@Override
		public void mousePressed(MouseEvent e){
			new Thread(new friendChatFrame(s,s1,i)).start();
		}
		@Override
		public void mouseExited(MouseEvent e) {
		}
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseClicked(MouseEvent e) {
		}
	}

	public void actionPerformed(ActionEvent e) {
		bnt_talk.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				chat.this.buttonUpdate();
				bnt_talk.setForeground(Color.LIGHT_GRAY);
				mainp.removeAll();
				mainp.validate();
				SwingUtilities.updateComponentTreeUI(pan_me);//刷新界面
				chat.this.talkPane();
			}
		});
		bnt_friend.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				chat.this.buttonUpdate();
				bnt_friend.setForeground(Color.LIGHT_GRAY);
				mainp.removeAll();
				mainp.validate();
				SwingUtilities.updateComponentTreeUI(pan_me);
				chat.this.friendPane();
			}
		});
		bnt_dyn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				chat.this.buttonUpdate();
				bnt_dyn.setForeground(Color.LIGHT_GRAY);
				mainp.removeAll();
				mainp.validate();
				SwingUtilities.updateComponentTreeUI(pan_me);
				chat.this.roomPane();
			}
		});
		bnt_search.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				chat.this.buttonUpdate();
				bnt_search.setForeground(Color.LIGHT_GRAY);
				mainp.removeAll();
				mainp.validate();
				SwingUtilities.updateComponentTreeUI(pan_me);
				chat.this.searchPane();
			}
		});
		bnt_ext.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
}

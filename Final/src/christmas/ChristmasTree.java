package christmas;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.sound.*;
import javax.sound.sampled.*;
import javax.swing.*;

public class ChristmasTree extends JFrame{
	Container c;
	ImageIcon tree_image, ornament_image, snow_image, light_image;
	JLabel tree;
	JPanel setButton, decoButton;
	JButton ornament, snow, light, reset, finish, save;
	int count=0;
	String deco = "";
	Clip clip;
	
	ChristmasTree(){
		setTitle("트리 꾸미기");
		Font f = new Font("",Font.BOLD,20);
		c=getContentPane();
		c.setBackground(Color.white);

		tree = new JLabel();
		tree.setHorizontalAlignment(JLabel.CENTER); // 가운데 배치
		tree_image = new ImageIcon("image/tree.png");
		tree.setIcon(tree_image);
		
		setButton = new JPanel();
		setButton.setLayout(new FlowLayout(FlowLayout.CENTER));
		decoButton = new JPanel();
		decoButton.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		ornament_image = new ImageIcon("image/ornament.png");
		ornament = new JButton(ornament_image); // 버튼에 이미지 삽입
		snow_image = new ImageIcon("image/snow.png");
		snow = new JButton(snow_image); // 버튼에 이미지 삽입
		light_image = new ImageIcon("image/light.png");
		light = new JButton(light_image); // 버튼에 이미지 삽입
		reset = new JButton("Reset");
		finish = new JButton("Finish");
		save = new JButton("SAVE");
		
		ornament.setFont(f); // 폰트 적용
		reset.setFont(f);
		finish.setFont(f);
		save.setFont(f);
		
		ornament.addActionListener(new Ornament());
		snow.addActionListener(new Snow());
		light.addActionListener(new Light());
		reset.addActionListener(new Reset());
		finish.addActionListener(new Finish());
		save.addActionListener(new Save());
		
		setButton.add(reset);
		setButton.add(finish);
		decoButton.add(ornament);
		decoButton.add(snow);
		decoButton.add(light);
		
		add(setButton, BorderLayout.NORTH);
		add(tree, BorderLayout.CENTER);
		add(decoButton, BorderLayout.SOUTH);
		
		setSize(500,700);
		setVisible(true);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				stopSound();
				dispose();
			}
		});
	}

	class Ornament implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			count++; // 추가된 장식품 개수
			deco += "o"; // 장식품 리스트 확인용
			if(count==1) { // 처음 추가된 장식품이라면
				tree_image = new ImageIcon("image/tree_o.png");
				tree.setIcon(tree_image);
			}
			else if(count==2) {
				if(deco.equals("lo")) { // 이전에 light를 추가했다면
					tree_image = new ImageIcon("image/tree_lo.png");
					tree.setIcon(tree_image);
				}
				else if(deco.equals("so")) { // 이전에 snow를 추가했다면
					deco = "os";
					tree_image = new ImageIcon("image/tree_os.png");
					tree.setIcon(tree_image);
				}
			}
			else JOptionPane.showMessageDialog(null, "더이상 선택할 수 없습니다. (MAX:2)");
		}
	}
	
	class Snow implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			count++;
			deco += "s";
			if(count==1) {
				tree_image = new ImageIcon("image/tree_s.png");
				tree.setIcon(tree_image);
			}
			else if(count==2) {
				if(deco.equals("ls")) { // 이전에 light를 추가했다면
					tree_image = new ImageIcon("image/tree_ls.png");
					tree.setIcon(tree_image);
				}
				else if(deco.equals("os")) { // 이전에 ornament를 추가했다면
					tree_image = new ImageIcon("image/tree_os.png");
					tree.setIcon(tree_image);
				}
			}
			else JOptionPane.showMessageDialog(null, "더이상 선택할 수 없습니다. (MAX:2)");
		}
		
	}
	
	class Light implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			count++;
			deco += "l";
			if(count==1) {
				tree_image = new ImageIcon("image/tree_l.png");
				tree.setIcon(tree_image);
			}
			else if(count==2) {
				if(deco.equals("ol")) { // 이전에 ornament를 추가했다면
					deco = "lo";
					tree_image = new ImageIcon("image/tree_lo.png");
					tree.setIcon(tree_image);
				}
				else if(deco.equals("sl")) { // 이전에 snow를 추가했다면
					deco = "ls";
					tree_image = new ImageIcon("image/tree_ls.png");
					tree.setIcon(tree_image);
				}
			}
			else JOptionPane.showMessageDialog(null, "더이상 선택할 수 없습니다. (MAX:2)");
		}
		
	}
	
	class Reset implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			count=0; // 추가된 장식품 개수 초기화
			deco=""; // 장식품 리스트 초기화
			tree_image = new ImageIcon("image/tree.png");
			tree.setIcon(tree_image); // 기본 트리 이미지로 변경
		}
		
	}
	
	class Finish implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			tree_image = new ImageIcon("image/tree_"+deco+"_final.png");
			tree.setIcon(tree_image);
			setButton.removeAll(); // setButton 패널에 있는 버튼 모두 삭제
			decoButton.removeAll(); // decoButton 패널에 있는 버튼 모두 삭제
			decoButton.add(save); // save 버튼 추가
			decoButton.revalidate(); // 레이아웃 변화 재확인 및 업데이트
			
			Sound("merry-christmas.wav"); // finish 버튼 눌렸을 때 음악파일 재생
			// mp3보단 wav가 java 기본 라이브러리로 재생 편리
		}
		
	}
	
	class Save implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			try {
				File output = new File("saved_tree.png"); // 저장할 외부 파일
				Component comp = tree;
				BufferedImage img = new BufferedImage(comp.getWidth(), comp.getHeight(), BufferedImage.TYPE_INT_ARGB);
				comp.paint(img.getGraphics());
				ImageIO.write(img, "png", output); // 파일에 이미지 저장
				JOptionPane.showMessageDialog(null, "이미지가 저장되었습니다: saved_tree.png");
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "저장 중 오류가 발생했습니다.");
			}
		}
		
	}
	
	public static void Sound(String file) { // 음악파일 실행 코드
		try {
			AudioInputStream song = AudioSystem.getAudioInputStream(new File(file));
			Clip clip = AudioSystem.getClip();
			clip.open(song);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "음악 파일 재생 실패");
		}
	}
	
	public void stopSound() {
		if(clip != null && clip.isRunning()) {
			clip.stop();
			clip.close();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ChristmasTree();
	}

}

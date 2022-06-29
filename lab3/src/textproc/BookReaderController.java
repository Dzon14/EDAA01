package textproc;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.Position;

public class BookReaderController {

	public BookReaderController(GeneralWordCounter counter) {
		SwingUtilities.invokeLater(() -> createWindow(counter, "Bookreader", 100, 300));
	}
	
	private void createWindow(GeneralWordCounter counter, String title, int width, int height) {
		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		// pane är en behållarkomponent till vilken de övriga komponenterna(listvy, knappar etc.) ska läggas till.
		Container pane = frame.getContentPane(); 
		
		SortedListModel<Entry<String, Integer>> m = new SortedListModel<>(counter.getWordList());
		JList<Entry<String, Integer>> list = new JList<>(m);
		JScrollPane scroll = new JScrollPane(list);
		
		pane.add(scroll);
		
		JPanel panel = new JPanel();
		
		//Knappar
		JButton buttonFirst = new JButton("Alphabetic");
		JButton buttonSecond = new JButton("Frequency");
		JButton buttonSearch = new JButton("search");
		panel.add(buttonFirst);
		panel.add(buttonSecond);
		
		pane.add(panel, BorderLayout.SOUTH);
		
		//Textfält
		JTextField search = new JTextField();
		search.setPreferredSize(new Dimension(200,25));
		panel.add(search);
		panel.add(buttonSearch);
		
		buttonFirst.addActionListener(e -> m.sort((a,b) -> a.getKey().compareTo(b.getKey())));
		buttonSecond.addActionListener(e -> m.sort((a,b) -> b.getValue() - a.getValue()));
		
		buttonSearch.addActionListener(e -> {
			String text = search.getText().trim().toLowerCase();
			int i = list.getNextMatch(text, 0, Position.Bias.Forward);
			
			if(i == -1) {
				JOptionPane.showMessageDialog(null, "Inga resultat hittades");
				search.setText("");
			} 
			 
			list.setSelectedIndex(i);
			list.ensureIndexIsVisible(i);
										});
		
		search.addActionListener(e -> buttonSearch.doClick());
		
		
		frame.pack();
		frame.setVisible(true);
	}
}

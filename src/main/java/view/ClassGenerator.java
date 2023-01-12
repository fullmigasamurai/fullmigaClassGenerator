/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package view;

import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JOptionPane;

import classGenerator.ArqSelector;

/**
 *
 * @author Toph
 */
public class ClassGenerator extends javax.swing.JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Creates new form ClassGenerator
	 */
	public ClassGenerator() {
		initComponents();
	}
	public boolean isArq (String name){
		java.io.BufferedReader reader = null;
		try {
			reader = new java.io.BufferedReader(new java.io.FileReader(name));
			reader.close();

		} catch (FileNotFoundException ex) {

			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} 

		return true;
	}
	public boolean loadArq(String name) {
		java.io.BufferedReader reader = null;
		try {
			if (!name.contains(".csv"))
				name+=".csv";
			reader = new java.io.BufferedReader(new java.io.FileReader(name));

		} catch (FileNotFoundException ex) {

			return false;
		}

		try {

			String nextLine, readingErrors="";
			for (int i = 0; i < 8 && (nextLine = reader.readLine()) != null; i++) {
				String split[] = nextLine.split(";");
				if (split.length < 2) {
					readingErrors+="Error reading line " + (i+1) + "\n";
				} else {
					table.setValueAt(split[0], i, 0);
					table.setValueAt(split[1], i, 1);
				}
			}
			if (readingErrors != "")
				JOptionPane.showMessageDialog(this, "Error reading file, msut have 2 columns\n"+readingErrors);
			reader.close();
			System.out.println("Arq Ready");
			arqNameLabel.setText(new java.io.File(name).getName().replace(".csv", ""));

		} catch (IOException ex) {
			JOptionPane.showMessageDialog(this, "Error reading file");
			return false;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error reading file");
		}

		return true;
	}

	public void saveArq(String nome) {
        java.io.BufferedWriter writer = null;
        try {
            writer = new java.io.BufferedWriter(new java.io.FileWriter(nome + ".csv"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "File could not be opened to writing");
        } finally {
            try {
				HashMap<String, String> data = readTable(table);
				
				for(HashMap.Entry<String, String> entry : data.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
						writer.write(key+";"+value+";");
						writer.write(System.getProperty("line.separator"));
				}

                writer.close();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error, writing not succed");

            }
        }
    }
	
	public void setCSV() {

		javax.swing.JFileChooser arq;
		arq = new javax.swing.JFileChooser();

		arq.setCurrentDirectory(new java.io.File(System.getProperty("user.dir")));
		javax.swing.filechooser.FileNameExtensionFilter filter = new javax.swing.filechooser.FileNameExtensionFilter(
				"Arquivos csv", "csv");
		arq.setFileFilter(filter);

		int retorno = arq.showOpenDialog(null);

		if (retorno == javax.swing.JFileChooser.APPROVE_OPTION) {
			confPathText.setText(arq.getSelectedFile().getAbsolutePath());
		} else if (retorno != javax.swing.JFileChooser.CANCEL_OPTION) {
			JOptionPane.showMessageDialog(rootPane, "Fail to select file");
		}
	}

	public String getFolderPath() {

		javax.swing.JFileChooser arq;
		arq = new javax.swing.JFileChooser();
		arq.setCurrentDirectory(new java.io.File(System.getProperty("user.dir")));
		arq.setDialogTitle("Select The Source Folder");
		arq.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
		arq.setAcceptAllFileFilterUsed(false);
  
		int retorno = arq.showOpenDialog(this);

		if (retorno == javax.swing.JFileChooser.APPROVE_OPTION) {
			return arq.getSelectedFile().getAbsolutePath();
		} else if (retorno != javax.swing.JFileChooser.CANCEL_OPTION) {
			JOptionPane.showMessageDialog(rootPane, "Fail to select directory");
			return null;
		}
		
		return null;
		
	}
	
	public HashMap<String, String> readTable(javax.swing.JTable tab) {

		HashMap<String, String> table = new HashMap<String, String>();

		int rows = tab.getRowCount();
		for (int i = 0; i < rows; i++) {
			if (tab.getValueAt(i, 0) != null 
				&& tab.getValueAt(i, 0) !="" 
				&& tab.getValueAt(i, 1) != null 
				&& tab.getValueAt(i, 1) != "") {

				table.put((String) tab.getValueAt(i, 0), (String) tab.getValueAt(i, 1));
			}

		}
		return table.isEmpty() ? null : table;


	}
		
	public void cleanTable() {

		int row = table.getRowCount(), col = table.getColumnCount();

		System.out.println("in clean");
		
		for (int i = 0; i < row; i++) {
			if (table.isRowSelected(i)){
				for (int j = 0; j < col; j++) {
					table.setValueAt(null, i, j);
				}
			}
		}
		
		confPathText.setText("");
		arqNameLabel.setText("");
	}

	public void renameArqs() {

		HashMap<String, String> tableMap = readTable(table);
		if (tableMap != null) {
			ArqSelector arqManipulator = new ArqSelector(tableMap);
			if (sourcePath.getText().isEmpty() ) {
				arqManipulator.listFolder(new java.io.File(System.getProperty("user.dir")));
				System.out.println("renaming at usr dir: " + System.getProperty("user.dir"));
			} else {
				if (isArq(sourcePath.getText())) {
				arqManipulator.listFolder(new java.io.File(sourcePath.getText()));
					System.out.println("renaming at: sourcepath:" + sourcePath.getText() + ":");
				}
				else if (JOptionPane.showConfirmDialog(this, "Destination canot be resolved, Wanna choose another?",
					"Error Path not found", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
						sourcePath.setText(getFolderPath());
				}
			}
			
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BackGroundPanel = new javax.swing.JPanel();
        TittleLabe = new javax.swing.JLabel();
        TablePanel = new javax.swing.JPanel();
        ScrollForJTable = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        FooterLabel = new javax.swing.JLabel();
        RightPanel = new javax.swing.JPanel();
        readArq = new javax.swing.JButton();
        generateArq = new javax.swing.JButton();
        confPathText = new javax.swing.JTextField();
        rename = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        sourcePath = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        usingArqLabel = new javax.swing.JLabel();
        arqNameLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ClassGenerator");
        setBackground(new java.awt.Color(51, 0, 51));
        setMinimumSize(new java.awt.Dimension(750, 300));

        BackGroundPanel.setBackground(new java.awt.Color(0, 51, 51));

        TittleLabe.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        TittleLabe.setForeground(new java.awt.Color(204, 255, 255));
        TittleLabe.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TittleLabe.setText("ClassGenerator");

        TablePanel.setBackground(new java.awt.Color(51, 0, 51));

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "OldName", "NewName"
            }
        ));
        table.setGridColor(new java.awt.Color(0, 0, 0));
        table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tableKeyPressed(evt);
            }
        });
        ScrollForJTable.setViewportView(table);
        table.setRowHeight(18);

        javax.swing.GroupLayout TablePanelLayout = new javax.swing.GroupLayout(TablePanel);
        TablePanel.setLayout(TablePanelLayout);
        TablePanelLayout.setHorizontalGroup(
            TablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ScrollForJTable, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
                .addContainerGap())
        );
        TablePanelLayout.setVerticalGroup(
            TablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ScrollForJTable, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        FooterLabel.setForeground(new java.awt.Color(153, 255, 153));
        FooterLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        FooterLabel.setText("Iel Rebeque @fullmigasamurai  ");

        RightPanel.setBackground(new java.awt.Color(51, 51, 51));
        RightPanel.setMaximumSize(new java.awt.Dimension(180, 178));
        RightPanel.setMinimumSize(new java.awt.Dimension(100, 100));

        readArq.setText("ReadArq");
        readArq.setToolTipText("Reads a aconfig file. gets the name from conf");
        readArq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                readArqActionPerformed(evt);
            }
        });

        generateArq.setText("Generate Arq");
        generateArq.setToolTipText("Generate a conf file with table contents; Uses conf field to get the name");
        generateArq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateArqActionPerformed(evt);
            }
        });

        confPathText.setToolTipText("Select a confgiguration file. csv type, two columns");
        confPathText.setMaximumSize(new java.awt.Dimension(180, 25));
        confPathText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                confPathTextMouseClicked(evt);
            }
        });
        confPathText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                confPathTextKeyPressed(evt);
            }
        });

        rename.setText("Rename");
        rename.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                renameActionPerformed(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(102, 255, 204));
        jLabel1.setText("Path:");

        sourcePath.setToolTipText("Select the directory where the files will me changed recursively");
        sourcePath.setMaximumSize(new java.awt.Dimension(180, 25));
        sourcePath.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sourcePathMouseClicked(evt);
            }
        });
        sourcePath.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sourcePathKeyPressed(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(102, 255, 153));
        jLabel2.setText("Conf:");

        javax.swing.GroupLayout RightPanelLayout = new javax.swing.GroupLayout(RightPanel);
        RightPanel.setLayout(RightPanelLayout);
        RightPanelLayout.setHorizontalGroup(
            RightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RightPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(RightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(readArq, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(generateArq, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rename, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RightPanelLayout.createSequentialGroup()
                        .addGroup(RightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(RightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(confPathText, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sourcePath, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        RightPanelLayout.setVerticalGroup(
            RightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RightPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(readArq)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(generateArq)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(RightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(confPathText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(RightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sourcePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(rename)
                .addContainerGap())
        );

        usingArqLabel.setForeground(new java.awt.Color(255, 255, 255));
        usingArqLabel.setText("Utilizando Arquivo:");

        arqNameLabel.setForeground(new java.awt.Color(153, 255, 255));
        arqNameLabel.setText("none");

        javax.swing.GroupLayout BackGroundPanelLayout = new javax.swing.GroupLayout(BackGroundPanel);
        BackGroundPanel.setLayout(BackGroundPanelLayout);
        BackGroundPanelLayout.setHorizontalGroup(
            BackGroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BackGroundPanelLayout.createSequentialGroup()
                .addGroup(BackGroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BackGroundPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(TittleLabe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(BackGroundPanelLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(BackGroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(BackGroundPanelLayout.createSequentialGroup()
                                .addComponent(usingArqLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(arqNameLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(FooterLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(BackGroundPanelLayout.createSequentialGroup()
                                .addComponent(TablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(RightPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        BackGroundPanelLayout.setVerticalGroup(
            BackGroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BackGroundPanelLayout.createSequentialGroup()
                .addComponent(TittleLabe, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BackGroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(RightPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BackGroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FooterLabel)
                    .addComponent(usingArqLabel)
                    .addComponent(arqNameLabel))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BackGroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BackGroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void generateArqActionPerformed(java.awt.event.ActionEvent evt) {    
		saveArq(confPathText.getText());
	
	}

	private void renameActionPerformed(java.awt.event.ActionEvent evt) {                                       
		renameArqs();

	}

	private void sourcePathKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
			if (sourcePath.getText().isEmpty()) {
				sourcePath.setText(getFolderPath());
			}
			else {
				renameArqs();
			}
        }
    }

    private void sourcePathMouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            sourcePath.setText(getFolderPath());
        }
    }

	private void readArqActionPerformed(java.awt.event.ActionEvent evt) {
		if (loadArq(confPathText.getText()) == false) {
			if (JOptionPane.showConfirmDialog(this, "File canot be opened for reading \nSelect another file?",
					"Error Opening File", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
				setCSV();
				loadArq(confPathText.getText());
			}
		}
	}

	private void confPathTextKeyPressed(java.awt.event.KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
			
			if (loadArq(confPathText.getText()) == false) {
				if (JOptionPane.showConfirmDialog(this, "File canot be opened for reading \nSelect another file?",
						"Error Opening File", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
					setCSV();
					loadArq(confPathText.getText());
				}
			}
		}
	}

	private void tableKeyPressed(java.awt.event.KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
			cleanTable();
			System.out.println("del");
		}
	}

    private void confPathTextMouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            confPathText.setText(getFolderPath());
        }
    }

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
		/* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
		* For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
		*/
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					javax.swing.UIDefaults defaults = javax.swing.UIManager.getLookAndFeelDefaults();
					defaults.put("Table.gridColor", new java.awt.Color (214,217,223));
					defaults.put("Table.disabled", false);
					defaults.put("Table.showGrid", true);
					defaults.put("Table.intercellSpacing", new java.awt.Dimension(1, 1));
					break;
				}
				
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(ClassGenerator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(ClassGenerator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(ClassGenerator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(ClassGenerator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		};
		//</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
			
				new ClassGenerator().setVisible(true);
			}
		});
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BackGroundPanel;
    private javax.swing.JLabel FooterLabel;
    private javax.swing.JPanel RightPanel;
    private javax.swing.JScrollPane ScrollForJTable;
    private javax.swing.JPanel TablePanel;
    private javax.swing.JLabel TittleLabe;
    private javax.swing.JLabel arqNameLabel;
    private javax.swing.JTextField confPathText;
    private javax.swing.JButton generateArq;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton readArq;
    private javax.swing.JButton rename;
    private javax.swing.JTextField sourcePath;
    private javax.swing.JTable table;
    private javax.swing.JLabel usingArqLabel;
    // End of variables declaration//GEN-END:variables
}

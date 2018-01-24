package funAnalysis;

/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import net.imagej.ImageJ;
import net.imagej.ops.OpService;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.Img;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.FloatType;

import org.scijava.app.StatusService;
import org.scijava.command.CommandService;
import org.scijava.log.LogService;
import org.scijava.thread.ThreadService;
import org.scijava.ui.UIService;

import ij.IJ;
import ij.ImagePlus;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class DeconvolutionDialog2 extends JDialog implements ItemListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OpService ops;
	private LogService log;
	private StatusService status;
	private CommandService cmd;
	private ThreadService thread;
	private UIService ui;
	private JComboBox<Plate> comboBox;
	private final JPanel contentPanel = new JPanel();
	private JTextField txt_FolderPath;
	private JTextField textField;
	private JLabel lblFolder;
	private JButton btnLoad;
	private JLabel lblOutputPrefix;
	private JLabel lblPlate;
	private JButton btnLoadImages;
	private JButton btnSporeCount;
	private JButton btnGim;
	private JButton btnDeconvolveViaCommand;
	private JPanel buttonPane;
	private JButton okButton;
	private boolean autoLoad = false;  
	private JCheckBox chckbxAutoLoad;

	private Experiment experiment;
	private JButton button_1;
	private JTable table;
	private JTable table_1;
	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		
		try {
			final ImageJ ij = new ImageJ();
			ij.launch(args);
			final DeconvolutionDialog2 dialog = new DeconvolutionDialog2();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
		catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DeconvolutionDialog2() {
		setResizable(false);
		setBounds(100, 100, 450, 580);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			btnDeconvolveViaCommand = new JButton(
				" Command");
			btnDeconvolveViaCommand.setBounds(135, 136, 112, 29);
			btnDeconvolveViaCommand.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent arg0) {
					deconvolveViaCommand();
				}
			});
			contentPanel.setLayout(null);
			{
				lblFolder = new JLabel("Folder");
				lblFolder.setBounds(8, 16, 39, 16);
				contentPanel.add(lblFolder);
			}
			{
				txt_FolderPath = new JTextField();
				txt_FolderPath.setBounds(52, 11, 310, 26);
				contentPanel.add(txt_FolderPath);
				txt_FolderPath.setColumns(25);
			}
			{
				btnLoad = new JButton("Load");
				btnLoad.setBounds(367, 10, 75, 29);
				btnLoad.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						openAnalysisFolder();
						setComboBoxModel();
						
						
					}
				});
				contentPanel.add(btnLoad);
			}
			{
				lblOutputPrefix = new JLabel("Output prefix");
				lblOutputPrefix.setBounds(8, 49, 85, 16);
				contentPanel.add(lblOutputPrefix);
			}
			{
				textField = new JTextField();
				textField.setBounds(98, 44, 264, 26);
				contentPanel.add(textField);
				textField.setColumns(25);
			}
			{
				{
					lblPlate = new JLabel("Plate");
					lblPlate.setBounds(8, 80, 30, 16);
					contentPanel.add(lblPlate);
				}
				{
					comboBox = new JComboBox<Plate>();
					comboBox.setBounds(98, 76, 149, 27);
					contentPanel.add(comboBox);
				}
				{
					btnLoadImages = new JButton("Load Images");
					btnLoadImages.setBounds(319, 75, 123, 29);
					contentPanel.add(btnLoadImages);
				}
			}
			{
				btnSporeCount = new JButton("Spore Count");
				btnSporeCount.setBounds(321, 136, 121, 29);
				contentPanel.add(btnSporeCount);
			}
			{
				btnGim = new JButton("GIM");
				btnGim.setBounds(246, 136, 75, 29);
				contentPanel.add(btnGim);
			}
		
			contentPanel.add(btnDeconvolveViaCommand);
		}
		final JButton btnDeconvolve = new JButton("ThreadService");
		btnDeconvolve.setBounds(8, 136, 131, 29);
		btnDeconvolve.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {

				// start deconvolution with the scijava ThreadService
				thread.run(() -> {
					deconvolve();
				});

			}
		});
		contentPanel.add(btnDeconvolve);
		
		chckbxAutoLoad = new JCheckBox("Auto load");
		chckbxAutoLoad.setBounds(319, 101, 128, 23);
		contentPanel.add(chckbxAutoLoad);
		chckbxAutoLoad.addItemListener(this);
		
		
		JButton button = new JButton(">");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//next dataset
				nextPlate();
				
			}
		});
		button.setBounds(171, 100, 77, 29);
		contentPanel.add(button);
		{
			button_1 = new JButton("<");
			button_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					previousPlate();
				}
			});
			button_1.setBounds(98, 100, 77, 29);
			contentPanel.add(button_1);
		}
		
		table = new JTable();
		table.setBounds(16, 153, 1, 1);
		contentPanel.add(table);
		
		JPanel panel = new JPanel();
		panel.setBounds(8, 166, 434, 347);
		contentPanel.add(panel);
		
		table_1 = new JTable();
		panel.add(table_1);
		{
			buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("Close ");
				okButton.setActionCommand("Close");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{contentPanel, lblFolder, txt_FolderPath, btnLoad, lblOutputPrefix, textField, lblPlate, comboBox, btnLoadImages, btnSporeCount, btnGim, btnDeconvolveViaCommand, btnDeconvolve, buttonPane, okButton}));
	}

	protected void previousPlate() {
		int currentPlate = comboBox.getSelectedIndex();
		int maxPlateNumber = comboBox.getItemCount()	;
		//System.out.println(currentPlate + " --- " +  maxPlateNumber);
			
		
		if(currentPlate == 0) {
			System.out.println(currentPlate + "--" + maxPlateNumber + "min ");
			//do nothing
		}
		else {// reach the max
			currentPlate = currentPlate-1;
			comboBox.setSelectedIndex(currentPlate);
		}
		//System.out.println("autoload is set to" + autoLoad);

		
		
	}

	protected void nextPlate() {
		int currentPlate = comboBox.getSelectedIndex();
		int maxPlateNumber = comboBox.getItemCount();
		

		if(currentPlate == maxPlateNumber - 1) {
			System.out.println(currentPlate + "--" + maxPlateNumber + "max");
			//do nothing
		}
		else {// reach the max
			currentPlate = currentPlate+1;
			comboBox.setSelectedIndex(currentPlate);
		}
		
		System.out.println(currentPlate + "----" + maxPlateNumber);
		System.out.println("autoload is set to" );
	}
	
	protected void setComboBoxModel() {
		
		//DLM = new DefaultComboBoxModel<>();
		Plate[]  plates = experiment.getPlates();
		for(int i = 0; i< plates.length; i++) {
			//DLM.add(plates[i].getName());
			//DLM.addListDataListener(l);
			this.comboBox.addItem(plates[i]);
		}
		comboBox.setRenderer(new PlateListCellRendenrer());
		comboBox.setSelectedIndex(0);
		//comboBox.setModel(DLM);
		
	}

	protected void openAnalysisFolder() {
		experiment = new Experiment();
		setUpdateROOTPath();
		
	}

	private void setUpdateROOTPath() {
		
		txt_FolderPath.setText(experiment.getPath());
		
	}

	/**
	 * Perform deconvolution
	 */
	public <T extends RealType<T>> void deconvolve() {
		final ImagePlus imp = IJ.getImage();

		final Img img = ImageJFunctions.wrap(imp);

		final Img<FloatType> imgFloat = ops.convert().float32(img);

		RandomAccessibleInterval<FloatType> psf = null;

		if (imgFloat.numDimensions() == 3) {
			psf = ops.create().kernelGauss(new double[] { 3, 3, 7 }, new FloatType());
		}
		else {
			psf = ops.create().kernelGauss(new double[] { 3, 3 }, new FloatType());
		}

		log.info("starting deconvolution with thread service");
		final RandomAccessibleInterval<T> deconvolved = ops.deconvolve()
			.richardsonLucy(imgFloat, psf, 50);
		log.info("finished deconvolution");

		ui.show(deconvolved);
	}

	/**
	 * perform deconvolution by calling a command
	 */
	public void deconvolveViaCommand() {

		final ImagePlus imp = IJ.getImage();
		final Img img = ImageJFunctions.wrap(imp);

		cmd.run(DeconvolutionCommand.class, true, "img", img, "sxy", 3, "sz", 7,
			"numIterations", 50);

	}

	public OpService getOps() {
		return ops;
	}

	public void setOps(final OpService ops) {
		this.ops = ops;
	}

	public LogService getLog() {
		return log;
	}

	public void setLog(final LogService log) {
		this.log = log;
	}

	public StatusService getStatus() {
		return status;
	}

	public void setStatus(final StatusService status) {
		this.status = status;
	}

	public CommandService getCommand() {
		return cmd;
	}

	public void setCommand(final CommandService command) {
		this.cmd = command;
	}

	public ThreadService getThread() {
		return thread;
	}

	public void setThread(final ThreadService thread) {
		this.thread = thread;
	}

	public UIService getUi() {
		return ui;
	}

	public void setUi(final UIService ui) {
		this.ui = ui;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object source = e.getItemSelectable();
		 
        if (source == chckbxAutoLoad) {
        		this.autoLoad = chckbxAutoLoad.isSelected();
        	 }
        		System.out.println(autoLoad);
		
			
	}
}
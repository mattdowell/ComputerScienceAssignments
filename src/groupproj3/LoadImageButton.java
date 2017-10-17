package groupproj3;

/**
 * this button is almost an exact copy of the LineButton.
 */
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class LoadImageButton extends JButton implements ActionListener {
    protected JPanel drawingPanel;
    protected View view;
    private MouseHandler mouseHandler;
    private LoadImageCommand loadImageCommand;
    private JFileChooser fileChooser;
    public LoadImageButton(View jFrame, JPanel jPanel) {
        super("Load Image");
        addActionListener(this);
        view = jFrame;
        drawingPanel = jPanel;
        mouseHandler = new MouseHandler();
        fileChooser = new JFileChooser();
        fileChooser.setDialogType(fileChooser.OPEN_DIALOG);
    }
    @Override
    public void actionPerformed(ActionEvent event) {
        view.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        drawingPanel.addMouseListener(mouseHandler);
    }

    private class MouseHandler extends MouseAdapter {
        private int pointCount = 0;
        @Override
        public void mouseClicked(MouseEvent event) {
            if (++pointCount == 1) {
                loadImageCommand = new LoadImageCommand(View.mapPoint(event.getPoint()));
                UndoManager.instance().beginCommand(loadImageCommand);
            } else if (pointCount == 2) {
                pointCount = 0;
                loadImageCommand.setImagePoint(View.mapPoint(event.getPoint()));
                int temp = fileChooser.showOpenDialog(drawingPanel);
                if(temp == JFileChooser.APPROVE_OPTION) {
                    loadImageCommand.setFile(fileChooser.getSelectedFile());
                }
                
                drawingPanel.removeMouseListener(this);
                view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                UndoManager.instance().endCommand(loadImageCommand);
            }
        }
    }
}
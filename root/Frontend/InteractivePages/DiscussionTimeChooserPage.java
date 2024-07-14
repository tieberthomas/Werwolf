package root.Frontend.InteractivePages;

import root.Frontend.InteractivePages.InteractiveElementsDtos.DiscussionTimeChooserPageElementsDto;
import root.Frontend.Utils.PageRefresherFramework.Models.InteractivePage;
import root.Frontend.Utils.TimeController;
import root.GameController;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DiscussionTimeChooserPage extends InteractivePage {

    private DiscussionTimeChooserPageElementsDto interactiveElementsDto;

    private JTextField discussionTimeTextField;
    private JButton next;
    private JButton back;

    private int discussionTimeMinutes = 17;

    @Override
    protected void setupObjects() {
        discussionTimeTextField = new JTextField();
        next = new JButton();
        back = new JButton();
    }

    @Override
    public void generatePage() {
        pageFactory.generateDiscussionTimeChooserPage(page, interactiveElementsDto);
    }

    @Override
    public void processActionEvent(ActionEvent ae) {
        if (next.equals(ae.getSource())) {
            String discussionTimeString = discussionTimeTextField.getText();
            try {
                discussionTimeMinutes = Integer.parseInt(discussionTimeString);
            } catch (NumberFormatException e) {
            }
            TimeController.discussionTimeMinutes = discussionTimeMinutes;
            erzählerFrame.currentInteractivePage = null;
            GameController.startGame();
        } else if (back.equals(ae.getSource())) {
            erzählerFrame.prevPage();
        }
    }

    @Override
    protected void setupPageElementsDtos() {
        interactiveElementsDto = new DiscussionTimeChooserPageElementsDto(discussionTimeTextField, next, back);
    }

}

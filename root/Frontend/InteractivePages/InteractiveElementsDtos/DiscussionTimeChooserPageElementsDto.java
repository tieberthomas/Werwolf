package root.Frontend.InteractivePages.InteractiveElementsDtos;

import javax.swing.*;

public class DiscussionTimeChooserPageElementsDto {

    public JTextField discussionTimeLengthTextField;
    public JButton next;
    public JButton back;

    public DiscussionTimeChooserPageElementsDto(JTextField discussionTimeLengthTextField, JButton next, JButton back) {
        this.discussionTimeLengthTextField = discussionTimeLengthTextField;
        this.next = next;
        this.back = back;
    }

}

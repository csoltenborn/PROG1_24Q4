package done;

public class Blatt6A1 {

    public record ListNode(int value, ListNode next) {}

    public record SinglyLinkedList(ListNode root) {}

    static SinglyLinkedList addFirst(final SinglyLinkedList list, final int value) {
        return new SinglyLinkedList(new ListNode(value, list.root()));
    }

    public static void main(String[] args) {
        SinglyLinkedList list = new SinglyLinkedList(new ListNode(42, null));
        list = addFirst(list, 23);
    }
    
}

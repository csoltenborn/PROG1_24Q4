package done;

public class Blatt6A1 {

    public record ListNode(int value, ListNode next) {}

    public record SinglyLinkedList(ListNode root) {}

    static SinglyLinkedList addFirst(final SinglyLinkedList list, final int value) {
        return new SinglyLinkedList(new ListNode(value, list.root()));
    }

    static SinglyLinkedList addLast(final SinglyLinkedList list, final int value) {
        return new SinglyLinkedList(addLast(list.root(), value));
    }

    static ListNode addLast(final ListNode current, final int value) {
        if (current == null) {
            return new ListNode(value, null);
        }
        return new ListNode(current.value, addLast(current.next(), value));
    }

    static String toString(final SinglyLinkedList list) {
        return toString(list.root());
    }

    static String toString(final ListNode current) {
        if (current == null) {
            return "";
        }
        String head = String.valueOf(current.value);
        String tail = toString(current.next());
        if ("".equals(tail)) {
            return head;
        }
        return head + ", " + tail;
    }

    public static void main(String[] args) {
        SinglyLinkedList list = new SinglyLinkedList(new ListNode(42, null));
        list = addFirst(list, 23);

        System.out.println(toString(list));

        list = addLast(list, 69);
        System.out.println(toString(list));
    }

}

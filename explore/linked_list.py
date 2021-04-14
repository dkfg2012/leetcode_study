class Node:
    def __init__(self, val):
        self.val = val
        self.next = None


class MyLinkedList:
    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.head = None
        self.count = 0

    def get(self, index: int) -> int:
        """
        Get the value of the index-th node in the linked list. If the index is invalid, return -1.
        """
        if index >= self.count or index < 0:
            return -1
        curr = self.head
        k = 0
        while index > k:
            k = k + 1
            curr = curr.next
        return curr.val

    def addAtHead(self, val: int) -> None:
        """
        Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list.
        """
        if not self.head:
            self.head = Node(val)
        else:
            new_node = Node(val)
            new_node.next = self.head
            self.head = new_node
        self.count += 1

    def addAtTail(self, val: int) -> None:
        """
        Append a node of value val to the last element of the linked list.
        """
        if self.count == 0:
            self.head = Node(val)
        else:
            curr = self.head
            while curr.next:
                curr = curr.next
            curr.next = Node(val)
        self.count += 1

    def addAtIndex(self, index: int, val: int) -> None:
        """
        Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted.
        """
        # self.printer()
        if index == 0:
            return self.addAtHead(val)
        # elif index == self.count:
        #    return self.addAtTail(val)
        elif index > self.count or index < 0:
            return
        else:
            curr = self.head
            new_node = Node(val)
            for _ in range(index - 1):
                curr = curr.next
            new_node.next = curr.next
            curr.next = new_node
            self.count += 1
        # self.printer()

    def deleteAtIndex(self, index: int) -> None:
        """
        Delete the index-th node in the linked list, if the index is valid.
        """
        if self.count == 1 and index == 0:
            self.head = None
            self.count -= 1
            return
        elif self.count == index + 1:
            curr = self.head
            while curr.next.next:
                curr = curr.next
            curr.next = None

        elif index == 0:
            self.head = self.head.next

        elif index + 1 > self.count or index < 0:
            return
        else:
            curr = self.head
            fast = self.head.next
            for _ in range(index - 1):
                curr = fast
                fast = fast.next
            curr.next = fast.next
        self.count -= 1
        # self.printer()

    def printer(self):
        curr = self.head
        while curr:
            print(curr.val, ' ')
            curr = curr.next

# Your MyLinkedList object will be instantiated and called as such:
# obj = MyLinkedList()
# param_1 = obj.get(1)
# obj.addAtHead(2)
# obj.addAtTail(3)
# obj.addAtIndex(2,5)
# obj.deleteAtIndex(2)
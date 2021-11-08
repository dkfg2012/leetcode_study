# Definition for singly-linked list.
# class ListNode(object):
#     def __init__(self, x):
#         self.val = x
#         self.next = None

class Solution(object):        
    def hasCycle(self, head):
        """
        :type head: ListNode
        :rtype: bool
        """
        try:
            slow = head
            fast = head
            while fast != None or fast.next != None:                
                slow = slow.next
                fast = fast.next.next
                if slow == fast:
                    return slow
        except:
            return False
        
    def detectCycle(self, head):
        """
        :type head: ListNode
        :rtype: ListNode
        """
        if head == None or head.next == None:
            return None
        intersectNode = self.hasCycle(head)
        if intersectNode == False:
            return None
        start = head
        while intersectNode != start:
            start = start.next
            intersectNode = intersectNode.next
        return start

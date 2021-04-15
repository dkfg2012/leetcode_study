# Definition for singly-linked list.
# class ListNode(object):
#     def __init__(self, x):
#         self.val = x
#         self.next = None

class Solution(object):
    def countLength(self, head):
            counter = 0
            while head.next:
                head = head.next
                counter += 1
            return counter
        
    def getIntersectionNode(self, headA, headB):
        """
        :type head1, head1: ListNode
        :rtype: ListNode
        """
        if headA == headB:
            return headA
        if headA == None or headB == None:
            return None
        Acounter = self.countLength(headA)
        Bcounter = self.countLength(headB)
        if Acounter > Bcounter:
            while Acounter > Bcounter:
                headA = headA.next
                Acounter -= 1
            while headA != headB:
                headA = headA.next
                headB = headB.next
            if(headA):
                return headA
            else:
                return None
        elif Bcounter > Acounter:
            while Bcounter > Acounter:
                headB = headB.next
                Bcounter -= 1
            while headA != headB:
                headA = headA.next
                headB = headB.next
            if(headB):
                return headB
            else:
                return None
        else:
            while headA != headB:
                headA = headA.next
                headB = headB.next
            if(headA):
                return headA
            else:
                return None
            
        

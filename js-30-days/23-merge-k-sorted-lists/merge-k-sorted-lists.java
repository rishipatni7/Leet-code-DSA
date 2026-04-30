import java.util.*;

class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> pq = new PriorityQueue<>((a, b) -> a.val - b.val);
        
        for(ListNode node : lists){
            if(node != null) pq.add(node);
        }
        
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        
        while(!pq.isEmpty()){
            ListNode node = pq.poll();
            curr.next = node;
            curr = curr.next;
            
            if(node.next != null) pq.add(node.next);
        }
        
        return dummy.next;
    }
}

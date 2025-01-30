public class minheap{
	private int[] heap;
	private int size;
	private int capacity;
	
	public minheap(int capacity){
		this.capacity=capacity;
		this.size=0;
		this.heap=new int[capacity];
	}
	private int parent(int i){
		return (i-1)/2;
	}
	private int leftChild(int i){
		return  (2*i)+1;
	}
	private int rightChild(int i){
		return (2*i)+2;
	}
        public void insert(int element){
        	if(size==capacity){
        		System.out.println("heap is full");
        		return;
        	}
        	heap[size]=element;
        	size++;
        	heapifyUp(size-1);
        }
        private void heapifyUp(int i){
        	while(i>0&&heap[parent(i)]>heap[i]){
        		int temp=heap[parent(i)];
        		heap[parent(i)]=heap[i];
        		heap[i]=temp;
        		i=parent(i);
        	}
        }
        public void deleteMin(){
        	if(size<=0){
        		System.out.println("Heap is empty");
        		return;
        	}
        	if(size==1){
        		size--;
        		return;
        	}
        	heap[0]=heap[size-1];
        	size--;
        	 heapifyDown(0);
        }
        public void heapifyDown(int i){
        	int smallest=i;
        	int left=leftChild(i);
        	int right=rightChild(i);
        	
        	if(left<size && heap[left]<heap[smallest]){
        		smallest=left;
        	}
        	if(right<size && heap[right]<heap[smallest]){
        		smallest=right;
        	}
        	if(smallest!=i){
        		int temp=heap[i];
        		heap[i]=heap[smallest];
        		heap[smallest]=temp;
        		heapifyDown(smallest);
        	}
        }
        public void printHeap(){
        	for(int i=0;i<size;i++){
        		System.out.print(heap[i]+" ");
        	}
        	System.out.println();
        }
        public static void main(String[] args){
        	minheap minHeap=new minheap(10);
        	minHeap.insert(3);
        	minHeap.insert(1);
        	minHeap.insert(6);
        	minHeap.insert(5);
        	minHeap.insert(2);
        	minHeap.insert(4);
        	
        	System.out.println("Min Heap:");
        	minHeap.printHeap();
        	System.out.println("Deleteing the minimum element(root)...");
        	minHeap.deleteMin();
        	System.out.println("min heap after deletion:");
        	minHeap.printHeap();
        }
   }

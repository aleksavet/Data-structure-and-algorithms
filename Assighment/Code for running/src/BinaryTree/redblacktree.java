package BinaryTree;

import java.util.LinkedList;
import java.util.Queue;

public class redblacktree<KEY extends Comparable<KEY>,DATA>
{
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class NODErb
    {
        KEY key;
        DATA data;
        NODErb left, right;
        boolean color;
        int size;

        NODErb(KEY key,DATA value, boolean color)
        {
            this.key = key;
            this.data = value;
            this.color = color;
            this.size = 1;
        }
    }

    private NODErb root;


    private int size(NODErb node)
    {
        if (node == null) {
            return 0;
        }
        return node.size;
    }

    public int size()
    {
        return size(root);
    }

    public boolean isEmpty()
    {
        return root == null;
    } //find if empty or not

    public void insert(KEY key, DATA value)
    {
        root = insert(root, key, value);
        root.color = BLACK;
    }

    private NODErb insert(NODErb node, KEY key, DATA value)
    {if (node== null)
        {
            return new NODErb(key, value, RED);
        }
        int find = key.compareTo(node.key);
        if (find < 0)
        {
            node.left = insert(node.left, key, value);
        } else if (find > 0)
        {
            node.right = insert(node.right, key, value);
        } else
        {
            node.data = value;
        }
        if (red(node.right) && !red(node.left))
        {
            node= rotateLeft(node);
        }
        if (red(node.left) && red(node.left.left))
        {
            node= rotateRight(node);
        }
        if (red(node.left) && red(node.right))
        {
            changing(node);
        }

        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    private NODErb rotateLeft(NODErb node)
    {
        NODErb knot=node.right;
        node.right = knot.left;  //changing nodes
        knot.left =node;
        knot.color =node.color;
        node.color = RED;
        knot.size =node.size;
        node.size = size(node.left) + size(node.right) + 1;
        return knot;
    }

    private NODErb rotateRight(NODErb node)
    {
        NODErb NEWNODE=node.left;
        node.left = NEWNODE.right;
        NEWNODE.right = node;
        NEWNODE.color=node.color;
        node.color = RED; //color change
       NEWNODE.size=node.size;
        node.size = size(node.left) + size(node.right) + 1;
        return NEWNODE;
    }

    private void changing(NODErb knot)
    {
        knot.color=!knot.color;
        knot.left.color =!knot.left.color;
        knot.right.color =!knot.right.color;
    }

    public void delete(KEY value) //delete node
    {
        if (!searching(value))//searching
        {
            return;
        }

        if (!red(root.left) && !red(root.right))
        {
            root.color = RED; //change color
        }

        root = delete(root, value);

        if (!isEmpty()) {
            root.color = BLACK;
        }
    }
    public boolean searching(KEY value)
    {
        return get(value) != null;
    }

    private NODErb delete(NODErb node,KEY value)
    {
        if (value.compareTo(node.key) < 0)
        {
            if (!red(node.left) && !red(node.left.left))
            {
                node=moveRedLeft(node);
            }
            node.left=delete(node.left, value);
        } else
        {if (red(node.left))
            {
                node=rotateRight(node);
            }if (value.compareTo(node.key) == 0 && node.right == null)
            {
                return null;
            }if (!red(node.right) && !red(node.right.left))
            {
                node=moveRedRight(node);
            }if (value.compareTo(node.key) == 0)
            {
                NODErb x=min(node.right);
                node.data=x.data;
                node.right=deleteMin(node.right);
            } else
            {
                node.right = delete(node.right, value);
            }
        }
        return balance(node); //after delete balance the colors of node
    }

    private NODErb balance(NODErb node)
    {
        if (red(node.right))
        {
            node = rotateLeft(node);
        }
        if (red(node.left) && red(node.left.left))
        {
            node = rotateRight(node);
        }
        if (red(node.left) && red(node.right))
        {
            changing(node); //change color
        }
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    private NODErb moveRedLeft(NODErb node)
    {
        changing(node);
        if (red(node.right.left)) {
            node.right = rotateRight(node.right);
            node = rotateLeft(node);           //change nodes
            changing(node);
        }
        return node;
    }
    private boolean red(NODErb node)  //check the color
    {
        if (node== null) {
            return false;
        }
        return node.color == RED;
    }
    private NODErb moveRedRight(NODErb node) //change node
    {
        changing(node);
        if (red(node.left.left))
        {
            node= rotateRight(node);
            changing(node); //change color
        }
        return node;
    }

    public void deleteMin()//help code if I want to delete minimum
    {
        if (isEmpty()) {
            return;
        }
        if (!red(root.left) && !red(root.right))
        {
            root.color = RED;
        }

        root = deleteMin(root);

        if (!isEmpty()) {
            root.color = BLACK;
        }
    }

    private NODErb deleteMin(NODErb node) //for min values
    {
        if (node.left == null)
        {
            return null;
        }

        if (!red(node.left) && !red(node.left.left))
        {
            node = moveRedLeft(node);
        }

        node.left = deleteMin(node.left);

        return balance(node);
    }


    private NODErb get(KEY key)
    {
        return get(root, key);
    } //helpcode for searching

    private NODErb get(NODErb node, KEY key)
    {
        while (node!= null)
        {
            int find = key.compareTo(node.key);
            if (find < 0)
            {
                node = node.left;
            } else if (find > 0)
            {
                node= node.right;
            } else
            {
                return node;
            }
        }
        return null;
    }

    public KEY min()
    {
        if (isEmpty())
        {
            throw new  IllegalStateException("Red-black tree is empty");
        }
        return min(root).key;
    }

    private NODErb min(NODErb node)//in left subtree find min key
    {
        if (node.left == null)
        {
            return node;
        }
        return min(node.left);
    }

    public KEY max()//help code
    {
        if (isEmpty())//check if empty
        {
            throw new  IllegalStateException("Red-black tree is empty");
        }
        return max(root).key;
    }

    private NODErb max(NODErb node) //find max key in right subtree
    {
        if (node.right == null)
        {
            return node;
        }
        return max(node.right);
    }

    public void printInOrder()
    {
        printInOrder(root);
    }

    private void printInOrder(NODErb node) //printing nodes in order from little to large
    {
        if (node == null) {
            return;
        }
        printInOrder(node.left);
        System.out.print(node.key + " ");
        printInOrder(node.right);
    }


}
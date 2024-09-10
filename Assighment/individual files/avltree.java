package BinaryTree;

public class avltree
{
    class NODEavl {
        int key;
        int data;
        int velkost;
        NODEavl left_node;
        NODEavl right_node;
        NODEavl(int ran_key,int ran_value)
        {this.key=ran_key;
            this.data=ran_value;
            this.velkost =1;
            this.left_node=null;
            this.right_node=null;}
    }
    NODEavl Root;
    public avltree()
    {
        this.Root=null;
    }
    int height(NODEavl node)
    {
        if(node == null)
        {
            return 0;
        }
        return node.velkost;
    }
    int balancing_h(NODEavl node)
    {
        if(node==null)
        {
            return 0;
        }
        return height(node.left_node)-height(node.right_node);
    }

    void fixing(NODEavl node)
    {
        node.velkost=Math.max(height(node.left_node),height(node.right_node))+1;
    }
    NODEavl rotation1(NODEavl knot)
    {
        NODEavl node_l = knot.left_node;
        NODEavl node_r = node_l.right_node;
        node_l.right_node=knot;
        knot.left_node=node_r;
        fixing(knot);
        fixing(node_l);
        return node_l;}
    NODEavl rotation2(NODEavl knot)
    {
        NODEavl node_r = knot.right_node;
        NODEavl node_l = node_r.left_node;
        node_r.left_node=knot;
        knot.right_node=node_l;
        fixing(knot);
        fixing(node_r);
        return node_r;
    }
    NODEavl balancing(NODEavl node)
    {
        fixing(node);
        int balancing = balancing_h(node);
        if(balancing>1)
        {
            if(balancing_h(node.left_node)<0)
            {
                node.left_node=rotation2(node.left_node);
            }return rotation1(node);
        }
        if(balancing<-1)
        {
            if(balancing_h(node.right_node)>0)
            {
                node.right_node =rotation1(node.right_node);
            }
            return rotation2(node);
        }
        return node;
    }

    public void inserting(int ran_key,int ran_value)
    {
        this.Root=insert(this.Root,ran_key,ran_value);
    }
    NODEavl insert(NODEavl root,int ran_key,int ran_data)
    {
        if(root == null)
        {
            return new NODEavl(ran_key,ran_data);
        }
        if(ran_key== root.key)
        {//if already exist key change value
            root.data=ran_data;
        } else if (ran_key<root.key)
        {
            root.left_node=insert(root.left_node,ran_key,ran_data);//in left subtree
        }else {
            root.right_node = insert(root.right_node,ran_key,ran_data); // in right subtree
        }
        return balancing(root);// balancing tree after each insert
    }

    public int searching(int key){
        NODEavl knot= search(this.Root,key);
        if(knot == null)
        {
            System.out.println("key not found");
        }
        return knot.data;
    }
    NODEavl search(NODEavl node,int key)
    {
        if(node==null||node.key==key)
        {
            return node;
        }
        if(key< node.key)
        {
            return search(node.left_node,key);
        }
        return search(node.right_node,key);
    }

    public void delete(int key)
    {
        this.Root=deleting(this.Root,key);
    }
    NODEavl deleting(NODEavl node,int key)
    {
        if (node == null) {
            return node;
        }
        if (key < node.key) {
            node.left_node = deleting(node.left_node, key);
        } else if (key > node.key) {
            node.right_node = deleting(node.right_node, key);
        } else {
            if ((node.left_node == null) || (node.right_node == null)) {
                NODEavl node2=null;
                if(node2==node.left_node)
                {
                    node2=node.right_node;
                }else {
                    node2=node.left_node;
                }
                if(node2==null)
                {
                    node2=node;
                    node=null;
                }else
                {
                    node=node2;
                }
            }
            else {
                NODEavl node2 = min(node.right_node);
                node.key= node2.key;
                node.right_node=deleting(node.right_node, node2.key);
            }
        }
        if(node==null)
        {
            return node;
        }
        node.velkost=max(height(node.left_node),height(node.right_node))+1;
        int balancing =balancing_h(node);
        if(balancing>1 && balancing_h(node.left_node)>=0)
        {
            return rotation1(node);
        }
        if(balancing>1&& balancing_h(node.left_node)<0)
        {
            node.left_node=rotation2(node.left_node);
            return rotation1(node);
        }
        if(balancing<-1 && balancing_h(node.right_node)<=0)
        {
            return rotation2(node);
        }
        if(balancing<-1 && balancing_h(node.right_node)>0)
        {
            node.right_node=rotation1(node.right_node);
            return rotation2(node);
        }
        return node;
    }
    int max(int val,int vall)
    {
        return (val>vall)?val:vall;
    }
    NODEavl min(NODEavl node)
    {
        while(node.left_node!= null)
        {
            node = node.left_node;
        }
        return node;
    }

}


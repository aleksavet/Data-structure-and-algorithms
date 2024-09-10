package HashTable;

public class quadraticprobing
{
    int size;
    int sizeoftable;
    String[] keys;
    String[] values;
    public quadraticprobing(int sizeoftable)
    {
        size=0;
        this.sizeoftable = sizeoftable;
        this.keys = new String[sizeoftable];
        this.values = new String[sizeoftable];

    }
    public void clear()
    {
        size=0;
        keys = new String[sizeoftable];
        values = new String[sizeoftable];
    }
    public int size()
    {
        return sizeoftable;
    }
    public boolean capacity()
    {
        return size==sizeoftable;
    }
    public boolean emptycheck()
    {
        return size() ==0;
    }
    public boolean searching(String key)
    {
        //return search(key) != null;
        //System.out.println(key);
        return true;
    }
    int hashing(String key)
    {
        return Math.abs(key.hashCode())%sizeoftable;
    }

    public String search(String key)
    {
        int i = hashing(key);
        int h=1;
        while (keys[i] != null)
        {
            if(keys[i].equals(key))
            {
                return values[i];

            }
            i=(i+h*h++)%sizeoftable;

        }
        return null;
    }
    public void insert(String key, String value)
    {
        if((double)size / sizeoftable >= 0.7)
        {
            resize(sizeoftable*2);
        }
        int hk=hashing(key);
        int i=hk;
        int h=1;
        do {

            if(keys[i] == null)
            {
                keys[i]=key;
                values[i]=value;
                size++;
                return;
            }
            if(keys[i].equals(key))
            {
                values[i]=value;
                return;
            }
            i=(i+h*h++)%sizeoftable;
        }
        while (i!=hk);
    }
    public void remove(String key)
    {
        if(!searching(key))
        {
            return;
        }
        int j = hashing(key);
        int h=1;
        while (!key.equals(keys[j]))
        {
            j=(j+h*h++)%sizeoftable;

        }
        keys[j]=values[j]=null;

        for(j=(j+h*h++)%sizeoftable; keys[j]!=null; j=(j+h*h++)%sizeoftable)
        {
            String key1=keys[j];
            String key2=values[j];
            keys[j]=values[j]=null;
            size--;
            insert(key1,key2);
        }
        size--;
    }

    public void resize(int size)
    {
        quadraticprobing tablenew = new quadraticprobing(size);

        for(int i =0; i<sizeoftable; i++)
        {
            if(keys[i] != null)
            {
                tablenew.insert(keys[i],values[i]);
            }
        }
        this.sizeoftable = size;
        this.keys=tablenew.keys;
        this.values=tablenew.values;
    }

    public void printing()
    {
        for(int i =0; i<sizeoftable;i++)
        {
            if(keys[i] != null)
            {
                System.out.println(keys[i]+ " "+ values[i]);
            }
            System.out.println();
        }
    }


}

package blackbird.com.recyclerviewdemo.test;

public final class SparseArrayS<E> /*implements Cloneable*/ {/*

    private static final Object DELETED = new Object();
    private boolean mGarbage = false;

    private int[] mKeys;
    private Object[] mValues;
    private int mSize;

    public SparseArrayS() {
        this(10);
    }

    public SparseArrayS(int initialCapacity) {
        if (initialCapacity == 0) {
            mKeys = EmptyArray.INT;
            mValues = EmptyArray.OBJECT;
        } else {
            mValues = ArrayUtils.newUnpaddedObjectArray(initialCapacity);
            mKeys = new int[mValues.length];
        }
        mSize = 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public SparseArray<E> clone() {
        SparseArray<E> clone = null;
        try {
            clone = (SparseArray<E>) super.clone();
            clone.mKeys = mKeys.clone();
            clone.mValues = mValues.clone();
        } catch (CloneNotSupportedException cnse) {
            *//* ignore *//*
        }
        return clone;
    }


    public E get(int key) {
        return get(key, null);
    }

    *//**
     * Gets the Object mapped from the specified key, or the specified Object
     * if no such mapping has been made.
     *//*
    @SuppressWarnings("unchecked")
    public E get(int key, E valueIfKeyNotFound) {
        int i = ContainerHelpers.binarySearch(mKeys, mSize, key);

        if (i < 0 || mValues[i] == DELETED) {
            return valueIfKeyNotFound;
        } else {
            return (E) mValues[i];
        }
    }


    public void delete(int key) {
        int i = ContainerHelpers.binarySearch(mKeys, mSize, key);

        if (i >= 0) {
            if (mValues[i] != DELETED) {
                mValues[i] = DELETED;
                mGarbage = true;
            }
        }
    }


    public E removeReturnOld(int key) {
        int i = ContainerHelpers.binarySearch(mKeys, mSize, key);

        if (i >= 0) {
            if (mValues[i] != DELETED) {
                final E old = (E) mValues[i];
                mValues[i] = DELETED;
                mGarbage = true;
                return old;
            }
        }
        return null;
    }


    public void remove(int key) {
        delete(key);
    }


    public void removeAt(int index) {
        if (mValues[index] != DELETED) {
            mValues[index] = DELETED;
            mGarbage = true;
        }
    }


    public void removeAtRange(int index, int size) {
        final int end = Math.min(mSize, index + size);
        for (int i = index; i < end; i++) {
            removeAt(i);
        }
    }

    private void gc() {
        // Log.e("SparseArray", "gc start with " + mSize);
        int n = mSize;
        int o = 0;
        int[] keys = mKeys;
        Object[] values = mValues;
        for (int i = 0; i < n; i++) {
            Object val = values[i];
            // 当前这个 value 不等于 DELETED
            if (val != DELETED) {
                if (i != o) {
                    // i != o
                    // 将索引 i 处的 key 赋值给 o 处的key
                    keys[o] = keys[i];
                    // 同时将值也赋值给 o 处
                    values[o] = val;
                    // 最后将 i 处的值置为空
                    values[i] = null;
                }
                // o 向后移动一位
                o++;
            }
        }
        mGarbage = false;
        mSize = o;
// Log.e("SparseArray", "gc end with " + mSize);

    }


    public void put(int key, E value) {
        int i = ContainerHelpers.binarySearch(mKeys, mSize, key); //二分查找.

        if (i >= 0) {  //如果当前这个i在数组中存在,那么表示插入了相同的key值,只需要将value的值进行覆盖..
            mValues[i] = value;
        } else {  //如果数组内部不存在的话,那么返回的数值必然是负数.
            i = ~i;  //因此需要取i的相反数.
            //i值小于mSize表示在这之前. mKey和mValue数组已经被申请了空间.只是键值被删除了.
            // 那么当再次保存新的值的时候.不需要额外的开辟新的内存空间.直接对数组进行赋值即可.
            if (i < mSize && mValues[i] == DELETED) {
                mKeys[i] = key;
                mValues[i] = value;
                return;
            }
            //当需要的空间要超出,但是mKey中存在无用的数值,那么需要调用gc()函数.
            if (mGarbage && mSize >= mKeys.length) {
                gc();

                // Search again because indices may have changed.
                i = ~ContainerHelpers.binarySearch(mKeys, mSize, key);
            }
            //如果需要的空间大于了原来申请的控件,那么需要为key和value数组开辟新的空间.
            if (mSize >= mKeys.length) {
                int n = ArrayUtils.idealIntArraySize(mSize + 1);
                //定义了一个新的key和value数组.需要大于mSize
                int[] nkeys = new int[n];
                Object[] nvalues = new Object[n];

                // Log.e("SparseArray", "grow " + mKeys.length + " to " + n);
                //对数组进行赋值也就是copy操作.将原来的mKey数组和mValue数组的值赋给新开辟的空间的数组.目的是为了添加新的键值对.
                System.arraycopy(mKeys, 0, nkeys, 0, mKeys.length);
                System.arraycopy(mValues, 0, nvalues, 0, mValues.length);
                //将数组赋值..这里只是将数组的大小进行扩大..放入键值对的操作不在这里完成.
                mKeys = nkeys;
                mValues = nvalues;
            }
            //如果i的值没有超过mSize的值.只需要扩大mKey的长度即可.
            if (mSize - i != 0) {
                // Log.e("SparseArray", "move " + (mSize - i));
                System.arraycopy(mKeys, i, mKeys, i + 1, mSize - i);
                System.arraycopy(mValues, i, mValues, i + 1, mSize - i);
            }
            //这里是用来完成放入操作的过程.
            mKeys[i] = key;
            mValues[i] = value;
            mSize++;
        }
    }

    *//**
     * Returns the number of key-value mappings that this SparseArray
     * currently stores.
     *//*
    public int size() {
        if (mGarbage) {
            gc();
        }

        return mSize;
    }

    *//**
     * Given an index in the range <code>0...size()-1</code>, returns
     * the key from the <code>index</code>th key-value mapping that this
     * SparseArray stores.
     * <p>
     * <p>The keys corresponding to indices in ascending order are guaranteed to
     * be in ascending order, e.g., <code>keyAt(0)</code> will return the
     * smallest key and <code>keyAt(size()-1)</code> will return the largest
     * key.</p>
     * <p>
     * <p>For indices outside of the range <code>0...size()-1</code>,
     * the behavior is undefined.</p>
     *//*
    public int keyAt(int index) {
        if (mGarbage) {
            gc();
        }

        return mKeys[index];
    }

    *//**
     * Given an index in the range <code>0...size()-1</code>, returns
     * the value from the <code>index</code>th key-value mapping that this
     * SparseArray stores.
     * <p>
     * <p>The values corresponding to indices in ascending order are guaranteed
     * to be associated with keys in ascending order, e.g.,
     * <code>valueAt(0)</code> will return the value associated with the
     * smallest key and <code>valueAt(size()-1)</code> will return the value
     * associated with the largest key.</p>
     * <p>
     * <p>For indices outside of the range <code>0...size()-1</code>,
     * the behavior is undefined.</p>
     *//*
    @SuppressWarnings("unchecked")
    public E valueAt(int index) {
        if (mGarbage) {
            gc();
        }

        return (E) mValues[index];
    }

    *//**
     * Given an index in the range <code>0...size()-1</code>, sets a new
     * value for the <code>index</code>th key-value mapping that this
     * SparseArray stores.
     * <p>
     * <p>For indices outside of the range <code>0...size()-1</code>, the behavior is undefined.</p>
     *//*
    public void setValueAt(int index, E value) {
        if (mGarbage) {
            gc();
        }

        mValues[index] = value;
    }

    *//**
     * Returns the index for which {@link #keyAt} would return the
     * specified key, or a negative number if the specified
     * key is not mapped.
     *//*
    public int indexOfKey(int key) {
        if (mGarbage) {
            gc();
        }

        return ContainerHelpers.binarySearch(mKeys, mSize, key);
    }

    *//**
     * Returns an index for which {@link #valueAt} would return the
     * specified key, or a negative number if no keys map to the
     * specified value.
     * <p>Beware that this is a linear search, unlike lookups by key,
     * and that multiple keys can map to the same value and this will
     * find only one of them.
     * <p>Note also that unlike most collections' {@code indexOf} methods,
     * this method compares values using {@code ==} rather than {@code equals}.
     *//*
    public int indexOfValue(E value) {
        if (mGarbage) {
            gc();
        }

        for (int i = 0; i < mSize; i++) {
            if (mValues[i] == value) {
                return i;
            }
        }

        return -1;
    }

    *//**
     * Returns an index for which {@link #valueAt} would return the
     * specified key, or a negative number if no keys map to the
     * specified value.
     * <p>Beware that this is a linear search, unlike lookups by key,
     * and that multiple keys can map to the same value and this will
     * find only one of them.
     * <p>Note also that this method uses {@code equals} unlike {@code indexOfValue}.
     *
     * @hide
     *//*
    public int indexOfValueByValue(E value) {
        if (mGarbage) {
            gc();
        }

        for (int i = 0; i < mSize; i++) {
            if (value == null) {
                if (mValues[i] == null) {
                    return i;
                }
            } else {
                if (value.equals(mValues[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    *//**
     * Removes all key-value mappings from this SparseArray.
     *//*
    public void clear() {
        int n = mSize;
        Object[] values = mValues;

        for (int i = 0; i < n; i++) {
            values[i] = null;
        }

        mSize = 0;
        mGarbage = false;
    }

    *//**
     * Puts a key/value pair into the array, optimizing for the case where
     * the key is greater than all existing keys in the array.
     *//*
    public void append(int key, E value) {
        if (mSize != 0 && key <= mKeys[mSize - 1]) {
            put(key, value);
            return;
        }

        if (mGarbage && mSize >= mKeys.length) {
            gc();
        }

        mKeys = GrowingArrayUtils.append(mKeys, mSize, key);
        mValues = GrowingArrayUtils.append(mValues, mSize, value);
        mSize++;
    }

    *//**
     * {@inheritDoc}
     * <p>
     * <p>This implementation composes a string by iterating over its mappings. If
     * this map contains itself as a value, the string "(this Map)"
     * will appear in its place.
     *//*
    @Override
    public String toString() {
        if (size() <= 0) {
            return "{}";
        }

        StringBuilder buffer = new StringBuilder(mSize * 28);
        buffer.append('{');
        for (int i = 0; i < mSize; i++) {
            if (i > 0) {
                buffer.append(", ");
            }
            int key = keyAt(i);
            buffer.append(key);
            buffer.append('=');
            Object value = valueAt(i);
            if (value != this) {
                buffer.append(value);
            } else {
                buffer.append("(this Map)");
            }
        }
        buffer.append('}');
        return buffer.toString();
    }
*/
}

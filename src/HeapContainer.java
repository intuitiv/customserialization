import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class HeapContainer {

	private static ThreadLocal<Heap> heap = new ThreadLocal<Heap>();

	public final static Heap get() {
		return (Heap) heap.get();
	}

	public static class Heap {
		ConcurrentHashMap<Integer, byte[]> hMap = new ConcurrentHashMap<Integer, byte[]>();
		ConcurrentHashMap<Integer, Object> hMapDeserial = new ConcurrentHashMap<Integer, Object>();

		public int add(Object aObj) throws IOException {
			if (!hMap.containsKey(aObj.hashCode())) {
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ObjectOutputStreamCustom out = new ObjectOutputStreamCustom(bos);
				ObjectOutputStreamCustom.internal = true;
				out.writeObject(aObj);
				hMap.put(aObj.hashCode(), bos.toByteArray());
				out.close();
			}
			return aObj.hashCode();
		}

		public Object get(Integer key) throws IOException {
			Object value = null;
			if (hMapDeserial.contains(key)) {
				value = hMapDeserial.get(key);
			}
			if (hMap.containsKey(key)) {
				ByteArrayInputStream bis = new ByteArrayInputStream(hMap.get(key));
				ObjectInputStreamCustom in = new ObjectInputStreamCustom(bis);
				try {
					value = in.readObject();
					hMapDeserial.put(key, value);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				in.close();
			}
			return value;
		}

		public Object getreference(Object object) {
			Integer key = object.hashCode();
			return hMap.get(key);
		}

		public boolean contains(Object object) {
			Integer key = object.hashCode();
			return hMap.containsKey(key);
		}
		
		public int getLength() {
			int len = 0;
			Iterator<byte[]> itr = hMap.values().iterator();
			while (itr.hasNext()) {
				byte[] bs = (byte[]) itr.next();
				len += bs.length;
			}
			return len;
		}
	}

	public final static void put(Heap aHeap) throws Exception {

		if (aHeap == null) {
			throw new Exception("cannot be null");
		}
		if (heap != null) {
			heap.set(aHeap);
		}
	}

	public final static void clear() {
		if (heap != null) {
			heap.set(null);
		}
	}
}

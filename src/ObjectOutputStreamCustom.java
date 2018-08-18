import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;


public class ObjectOutputStreamCustom extends ObjectOutputStream {

	static boolean internal = false;
	public ObjectOutputStreamCustom(OutputStream out) throws IOException {
		super(out);
		enableReplaceObject(true);
	}

	@Override
	protected Object replaceObject(Object obj) throws IOException {
		if(obj == null) {
			return -1;
		}
		HeapContainer.Heap hp = HeapContainer.get();
		if(hp.contains(obj)) {
			return hp.getreference(obj);
		}
		if(!internal) {
			obj = HeapContainer.get().add(obj);
		}
		internal = false;
		return obj;
	}
}

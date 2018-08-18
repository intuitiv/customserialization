import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class ObjectInputStreamCustom extends ObjectInputStream {

	public ObjectInputStreamCustom(InputStream in) throws IOException {
		super(in);
		enableResolveObject(true);
	}

	@Override
	protected Object resolveObject(Object obj) throws IOException {
		if (obj instanceof Integer) {
			if (((Integer) obj) == -1) {
				return null;
			}
			return HeapContainer.get().get((Integer) obj);
		}
		return obj;
	}

}

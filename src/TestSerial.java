import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class TestSerial {
	public static void main(String[] args) throws Exception {
		B b = new B("1", "2", "3", "4", "5");
		System.out.println(b);
		HeapContainer.put(new HeapContainer.Heap());
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStreamCustom out = new ObjectOutputStreamCustom(bos);
		out.writeObject(b);
		out.close();
		System.out.println(bos.toByteArray().length);
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		ObjectInputStreamCustom ois = new ObjectInputStreamCustom(bis);
		b = (B) ois.readObject();

		ois.close();
		System.out.println(b);
		
		System.out.println(HeapContainer.get().getLength());

	}
}

class A implements Serializable {
	String k;
	C c;

	A(String i, String j, String k) {
		c = new C(i, j);
		this.k = k;
	}

	@Override
	public String toString() {
		return k + "##" + c;
	}
}

class B extends A {
	C c1;

	B(String i1, String j1, String k, String i2, String j2) {
		super(i1, j1, k);
		c1 = new C(i2, j2);
	}

	@Override
	public String toString() {
		return "c1: " + c1 + "--" + super.toString();
	}
}

class C implements Serializable {
	String i;
	String j;

	C(String i, String j) {
		this.i = i;
		this.j = j;
	}

	@Override
	public String toString() {
		return i + "--" + j;
	}
}
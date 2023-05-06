package cn.howlingox.natcross.common;

/**
 * 
 * <p>
 * 操作对象，主要是让值能够通过引用进行传递
 * </p>
 *
 * @author howlingfox
 * @since 2022-02-13 16:35:46
 * @param <T> 存放的类型
 */
public class Optional<T> {

	public static <T> Optional<T> of(T value) {
		return new Optional<T>(value);
	}

	public Optional(T value) {
		this.value = value;
	}

	private T value;

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

}

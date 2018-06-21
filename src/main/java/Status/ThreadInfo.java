package Status;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ThreadInfo
{
	Thread thread;
	private StringProperty name = new SimpleStringProperty();
	private StringProperty stateString = new SimpleStringProperty();
	private IntegerProperty priority = new SimpleIntegerProperty();
	private StringProperty type = new SimpleStringProperty();

	public ThreadInfo(Thread thread)
	{
		this.thread = thread;

		name.set(thread.getName());
		stateString.set(thread.getState().toString());
		priority.set(thread.getPriority());
		type.set(thread.isDaemon() ? "Daemon" : "Normal");
	}

	public StringProperty NameProperty()
	{
		return name;
	}

	public StringProperty StateProperty()
	{
		return stateString;
	}

	public IntegerProperty PriorityProperty()
	{
		return priority;
	}

	public StringProperty TypeProperty()
	{
		return type;
	}

}

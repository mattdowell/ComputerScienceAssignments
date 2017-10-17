package org.ics141.program5;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for managing all the "Events" from the UI and
 * performing the necessary action on the output label. It does this by building
 * a map of components that perform actions, and their respective values. It
 * then applies the values to the UI output.
 * 
 * @author Matt
 * 
 */
public class EventBus implements ActionListener {

	private static final Map<Object, EventValue> COMPONENT_EVENT_MAP = new HashMap<Object, EventValue>(11);

	private NumberManager myNumberManager;

	public EventBus(NumberManager inDisp) {
		myNumberManager = inDisp;
	}

	/**
	 * Ads a source mapping to our Map
	 * @param inSource
	 * @param inValue
	 */
	public void addSourceMapping(Object inSource, EventValue inValue) {
		COMPONENT_EVENT_MAP.put(inSource, inValue);
	}

	/**
	 * This is a list of the possible event values
	 * 
	 * @author Matt
	 * 
	 */
	public static enum EventValue {
		One("1"), Two("2"), Three("3"), Four("4"), Five("5"), Six("6"), Seven("7"), Eight("8"), Nine("9"), Zero("0"), Star("*"), Pound("#"), Clear(
				"Clear");

		private String code;

		private EventValue(String c) {
			code = c;
		}

		public String getCode() {
			return code;
		}
	}

	/**
	 * Delegates to the number manager
	 */
	@Override
	public void actionPerformed(ActionEvent event) {

		EventValue v = COMPONENT_EVENT_MAP.get(event.getSource());
		myNumberManager.buttonPressed(v);

	}
}

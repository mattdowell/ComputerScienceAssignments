package groupproj2.iteration1;

/**
 * This interface should be implemented by any interface that is listening to configuration change events.
 *
 */
public interface RefridgeratorStateChangeListener {

	/**
	 * This method is called on every implementing class when the state changes.
	 * 
	 * @param inConf
	 */
	public void configurationChanged(RefridgeratorState inConf);

}

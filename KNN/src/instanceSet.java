import java.util.ArrayList;

public class instanceSet {
	private ArrayList<Double> instances = new ArrayList<Double>();
	private String classes;

	public instanceSet(ArrayList<Double> instances, String classes) {
		this.instances = instances;
		this.classes = classes;
	}

	/**
	 * @return returns the class the instances belong to
	 */
	public String getClasses() {
		return classes;
	}

	/**
	 * setting class for the instances
	 * 
	 * @param classes
	 */
	public void setClasses(String classes) {
		this.classes = classes;
	}

	/**
	 * @return returns an array of doubles of instances
	 */
	public ArrayList<Double> getInstances() {
		return instances;
	}

	/**
	 * sets the instances
	 * 
	 * @param instances
	 */
	public void setInstances(ArrayList<Double> instances) {
		this.instances = instances;
	}

	/**
	 * adding values into the list of instances
	 * 
	 * @param ins
	 */
	public void addInstances(double ins) {
		instances.add(ins);
	}

	/**
	 * distances between the two instances objects
	 * 
	 * @param o
	 *            instances object
	 * @return
	 */
	public double distance(instanceSet o) {

		double dis = 0;
		dis += (Math.pow(this.instances.get(0) - o.instances.get(0), 2) / Math
				.pow(KNN.sLengthRange, 2));
		dis += (Math.pow(this.instances.get(1) - o.instances.get(1), 2) / Math
				.pow(KNN.sWidthRange, 2));
		dis += (Math.pow(this.instances.get(2) - o.instances.get(2), 2) / Math
				.pow(KNN.pLengthRange, 2));
		dis += (Math.pow(this.instances.get(3) - o.instances.get(3), 2) / Math
				.pow(KNN.pWidthRange, 2));

		return Math.sqrt(dis);
	}

	@Override
	public String toString() {
		return this.instances.get(0) + ", " + this.instances.get(1) + ", "
				+ this.instances.get(2) + ", " + this.instances.get(3) + ", "
				+ classes + "\n";
	}

}

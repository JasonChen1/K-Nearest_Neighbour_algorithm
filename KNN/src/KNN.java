import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class KNN {

	private ArrayList<instanceSet> Set;
	String trainingFname;
	String testFname;

	public static double sLengthRange;
	public static double sWidthRange;
	public static double pLengthRange;
	public static double pWidthRange;
	private double sLengthMax = Double.MIN_VALUE;
	private double sLengthMin = Double.MAX_VALUE;
	private double sWidthMax = Double.MIN_VALUE;
	private double sWidthMin = Double.MAX_VALUE;
	private double plLengthMax = Double.MIN_VALUE;
	private double pLengthMin = Double.MAX_VALUE;
	private double pWidthMax = Double.MIN_VALUE;
	private double pWidthMin = Double.MAX_VALUE;

	public KNN(String trainingFName, String testFName) {
		this.trainingFname = trainingFName;
		this.testFname = testFName;

		// load the training set
		loadSet(trainingFName);
		// System.out.println(trainingSet.toString());

		// test the training set by calling loadtestset method
		loadTestSet(testFName);
	}

	/**
	 * load the file into the array and return it
	 * 
	 * @param filename
	 */
	public void loadSet(String filename) {

		Set = new ArrayList<instanceSet>();

		// getting path of the file
		String file = new File(filename).getAbsolutePath();

		try {
			// list to store the intances values
			ArrayList<Double> values;

			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;

			while ((line = reader.readLine()) != null && !line.equals("")) {
				values = new ArrayList<Double>();
				String[] val = line.split("  ");
				values.add(Double.parseDouble(val[0]));// sepal length
				values.add(Double.parseDouble(val[1]));// sepal width
				values.add(Double.parseDouble(val[2]));// petal length
				values.add(Double.parseDouble(val[3]));// petal width
				String classes = val[4];// the classes of each instances

				instanceSet i = new instanceSet(values, classes);
				Set.add(i);
				//call setRange method to set the range of the values 
				setRange(Double.parseDouble(val[0]),
						Double.parseDouble(val[1]), Double.parseDouble(val[2]),
						Double.parseDouble(val[3]));

			}

			reader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setRange(double sl, double sw, double pl, double pw) {
		
		// getting the maximum and minimum values of sepal and petal
		if (sLengthMax < sl) {
			sLengthMax = sl;
		}
		if (sLengthMin > sl) {
			sLengthMin = sl;
		}
		if (sWidthMax < sw) {
			sWidthMax = sw;
		}
		if (sWidthMin > sw) {
			sWidthMin = sw;
		}
		if (plLengthMax < pl) {
			plLengthMax = pl;
		}
		if (pLengthMin > pl) {
			pLengthMin = pl;
		}
		if (pWidthMax < pw) {
			pWidthMax = pw;
		}
		if (pWidthMin > pw) {
			pWidthMin = pw;
		}

		// range values
		sLengthRange = sLengthMax - sLengthMin;
		sWidthRange = sWidthMax - sWidthMin;
		pLengthRange = plLengthMax - pLengthMin;
		pWidthRange = pWidthMax - pWidthMin;

	}

	public void loadTestSet(String filename) {
		// ask user for input k
		System.out.printf("Please Enter \"k\" value:");
		Scanner input = new Scanner(System.in);
		int k = input.nextInt();

		// getting path of the file
		String file = new File(filename).getAbsolutePath();

		int count = 0;
		int correct = 0;

		try {
			// list to store the intances values
			ArrayList<Double> values;

			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;

			while ((line = reader.readLine()) != null && !line.equals("")) {
				values = new ArrayList<Double>();
				String[] val = line.split("  ");
				values.add(Double.parseDouble(val[0]));// sepal length
				values.add(Double.parseDouble(val[1]));// sepal width
				values.add(Double.parseDouble(val[2]));// petal length
				values.add(Double.parseDouble(val[3]));// petal width
				String classes = val[4];// the classes of each instances

				final instanceSet ins = new instanceSet(values, classes);
				Collections.sort(Set, new Comparator<instanceSet>() {
					@Override
					public int compare(instanceSet o1, instanceSet o2) {
						return Double.compare(ins.distance(o1),
								ins.distance(o2));
					}
				});

				// String is the class type Integer is the count of the
				// instances
				HashMap<String, Integer> neighbors = new HashMap<String, Integer>();

				// if k values is bigger than the size of the training set the
				// set it as the size of the training set
				if (Set.size() < k) {
					k = Set.size();
				}
				// adding the neighbors into the map
				for (int i = 0; i < k; i++) {
					if (neighbors.get(Set.get(i).getClasses()) == null) {
						neighbors.put(Set.get(i).getClasses(), 1);
					} else {
						neighbors.put(Set.get(i).getClasses(),
								neighbors.get(Set.get(i).getClasses()) + 1);
					}
				}

				// find out the class that has the most count
				int max = Integer.MIN_VALUE;
				String checkClass = null;
				for (Map.Entry<String, Integer> m : neighbors.entrySet()) {
					if (m.getValue() > max) {
						max = m.getValue();
						checkClass = m.getKey();
					}
				}

				// find out the correctness
				if (ins.getClasses().equals(checkClass)) {
					correct++;
				} else {
					System.out.println("Wrong Class: " + checkClass
							+ " should be: " + ins.getClasses());
				}

				// the total count of the datas
				count++;
			}

			reader.close();

			double accuracy = (double) correct / (double) count;
			System.out.println("k value is: " + k);
			System.out.println("Correct: " + correct + " out of " + count);
			System.out.println("Classification accuracy: "
					+ String.format("%.2f", accuracy * 100) + "%");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		if (args.length != 2) {
			System.out
					.println("Need atleast two arguments (first argument should be training set, second argument should be the test set)");
		} else {
			// the first filename should be the training set // the second
			// filename should be the test set
			KNN k = new KNN(args[0], args[1]);

		}

		// KNN k = new KNN("iris-training.txt", "iris-test.txt");
	}
}

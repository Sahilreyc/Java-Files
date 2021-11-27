import java.util.Scanner;  // Import the Scanner class
import java.io.*; // Import the I/O library
import java.util.ArrayList;


public class sc973_task1 {

	// ---------------------------------------------------------------------
	// Function: Empty Constructor
	// ---------------------------------------------------------------------
	public sc973_task1 ()
	{
	}

	public int print_move (int disc, int source_tower, int destination_tower, FileWriter writer)
	{
		try {
			System.out.printf("\nMove disk %d from T%d to T%d", disc, source_tower, destination_tower);
			writer.write("\n" + disc + "\t" +  source_tower + "\t" + destination_tower);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return 0;
	}

	// ---------------------------------------------------------------------
	// Function: The recursive function for tower_of_hanoi_four
	// Computes: Prints all the moves with disk numbers
	// ---------------------------------------------------------------------
	public void move (int numberOfDiscs, int numberOfTowers, int sourceTower, int destinationTower, FileWriter writer)
	{
		if (numberOfDiscs == 1)
		{
			print_move(numberOfDiscs, sourceTower, destinationTower, writer);
		}
		else
		{
			int bufferTower = numberOfTowers;
			while ((bufferTower == sourceTower) || (bufferTower == destinationTower) || (bufferTower == 0))
			{
				bufferTower = bufferTower - 1;
			}

			move(numberOfDiscs-1, numberOfTowers, sourceTower, bufferTower, writer);

			print_move(numberOfDiscs, sourceTower, destinationTower, writer);

			move(numberOfDiscs-1, numberOfTowers, bufferTower, destinationTower, writer);
		}
	}



	// ---------------------------------------------------------------------
	// Function: main
	// ---------------------------------------------------------------------
	public static void main(String[] args) {
		Scanner myObj = new Scanner(System.in);  // Create a Scanner object
		int n, t, s, d;
		String my_ID = new String("sc973"
		);

		if (args.length < 4)
		{
			System.out.printf("Usage: java %s_task1 <n> <t> <s> <d>\n", my_ID);
			return;
		}

		n = Integer.parseInt(args[0]);  // Read user input n
		t = Integer.parseInt(args[1]);  // Read user input t
		s = Integer.parseInt(args[2]);  // Read user input s
		d = Integer.parseInt(args[3]);  // Read user input d

		// Check the inputs for sanity
		if (n<1 || t<3 || s<1 || s>t || d<1 || d>t)
		{
			System.out.printf("Please enter proper parameters. (n>=1; t>=3; 1<=s<=t; 1<=d<=t)\n");
			return;
		}

		// Create the output file name
		String filename;
		filename = new String(my_ID + "_ToH_n" + n + "_t" + t + "_s" + s + "_d" + d + ".txt");
		try {
			// Create the Writer object for writing to "filename"
			FileWriter writer = new FileWriter(filename, true);

			// Write the first line: n, t, s, d
			writer.write(n + "\t" + t + "\t" + s + "\t" + d);

			// Create the object of this class to solve the generalised ToH problem
			sc973_task1 ToH = new sc973_task1();

			System.out.printf("Output on screen for (n = " + n + ", t = "+ t + ", s = "+ s + ", d = "+ d + "):");
			System.out.printf("\nThe output is also written to the file %s", filename);

			// Call the recursive function that solves the ToH problem
			ToH.move(n, t, s, d, writer);

			// Close the file
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.printf("\n");
		return;
	}
}

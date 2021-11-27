import java.util.*; // Import the util library
import java.io.*; // Import the I/O library

public class sc973_task2 {

	// ---------------------------------------------------------------------
	// Function: Empty Constructor
	// ---------------------------------------------------------------------
	public sc973_task2 ()
	{
	}

	// ---------------------------------------------------------------------
	// Function: isBlank
	// ---------------------------------------------------------------------
	public static boolean isBlank (int character) {
		if (
		character == ' ' ||
		character == '\t' ||
		character == '\n' ||
		character == '\r'
		)
			return true;
		return false;

	}

	// ---------------------------------------------------------------------
	// Function: getNextInteger
	// This function only works assuming that the file has positive integers
	// ---------------------------------------------------------------------
	public static int getNextInteger (FileInputStream input_file) {
		int character;
		int digit;
		int number = 0;
		try {
			while ((character = input_file.read()) != -1 && !isBlank(character))
			{
				number *= 10;
				digit = (int) character - '0';
				number += digit;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return number;
	}

	// ---------------------------------------------------------------------
	// Function: main
	// ---------------------------------------------------------------------
	public static void main(String[] args) {
            Scanner myObj = new Scanner(System.in);
            int n, t, s, d;
            String str_filename;
            String my_ID = new String("sc973");


            if (args.length < 1)
            {
              System.out.printf("Usage: java %s_task2 <file_name>\n", my_ID);//Check for input filename in args
              return;
            }

            try {
              str_filename = args[0];
              System.out.printf("Reading the file " + str_filename + "\n");//Find File name

              FileInputStream input_file = new FileInputStream(str_filename);//Create Object

              n = getNextInteger (input_file);//Read Parameters
              t = getNextInteger (input_file);
              s = getNextInteger (input_file);
              d = getNextInteger (input_file);
              System.out.printf("%d\t%d\t%d\n", n, t, s, d);

              int a = 0;//0 Will be the largest disc in the tower, while on the other hand n should be the smallest disk in the tower
              int[][] towers = new int[t][n]; //2D Array
              for (int i = n; i>0; i--)
              {
                towers[s-1][a]=i;
                a++;
              }

              boolean[] usedTowers = new boolean[t];

              int disc;//Current Disc being Moved
              int before;//The tower before
              int after;//The tower after
              int length = input_file.available();
              length = length - 4;
              int duration = 0;
              String currentTower = "";
              String error = "";
              System.out.println("The status of all the towers at the start is as follows:");
              for (int i = 0; i<t; i++)
              {
                currentTower = ("Tower "+ (i+1) + ": [");
                for (int f = 0; f<n; f++)
                {
                  currentTower = currentTower + (towers[i][f] + ", ");
                }
                currentTower.trim();
                currentTower = currentTower.substring(0, currentTower.length()-2);

                currentTower = currentTower + "]";
                System.out.println(currentTower);
              }

              while (duration <= length)
              {
                disc = getNextInteger(input_file);
                before = getNextInteger(input_file);
                after = getNextInteger(input_file);
                System.out.println("Move:    disc " + disc + " before tower " + before + " after tower " + after);//Print Move Line

                if ((disc > n) || (disc < 1))
                {
                  error = "Move Error: the disc " + disc + " is out of range";//Makes sure disc is in range
                  break;
                }
                if ((before > t) || (before < 1))
                {
                  error = "Move Error: the source tower: " + before + " is out of range";//Makes sure disc is in range
                  break;
                }
                if ((after > t) || (after < 1))
                {
                  error = "Move Error: the destination tower " + after + " is out of range";//Makes sure disc is in range
                  break;
                }

                System.out.println("Before the move:");

                String sourceTower = "Source tower ";//Storing source before move
                sourceTower = sourceTower + before + ": [";
                for (int f = 0; f<n; f++)
                {
                  if (towers[before-1][f] != 0 )
                  {
                    sourceTower = sourceTower + (towers[before-1][f] + ", ");
                  }
                }

                sourceTower.trim();
                if (sourceTower.contains(","))
                {
                  sourceTower = sourceTower.substring(0, sourceTower.length()-2);
                }
                sourceTower = sourceTower + "]";
                System.out.println(sourceTower);

                String destinationTower = "Destination tower ";//Storing destination before move

                destinationTower = destinationTower + after + ": [";
                for (int f = 0; f<n; f++)
                {
                  if (towers[after-1][f] != 0)
                  {
                    destinationTower = destinationTower + (towers[after-1][f]+ ", ");
                  }
                }
                destinationTower.trim();
                if (destinationTower.contains(","))
                {
                  destinationTower = destinationTower.substring(0, destinationTower.length()-2);
                }
                destinationTower = destinationTower + "]";
                System.out.println(destinationTower);

                int moveDisc_i = 0;
                for (int i = n-1; i >= 0; i--)
                {
                  if (towers[before-1][i] == 0)
                  {
                  }
                  else
                  {
                    moveDisc_i = i;
                    break;
                  }
                }
                if (towers[before-1][moveDisc_i] !=disc)
                {
                  error = error + "Move Error: ";
                  error = error + sourceTower;
                  error = error + " " + moveDisc_i + "" + " is not on the top of the source tower";
                  System.out.println(error);
                  System.out.println("");
                  break;
                }

                int i_OpenDestination = 0;
                for (int i = n-1; i >= 0; i--)
                {
                  if (towers[after-1][i] == 0)
                  {

                  }
                  else
                  {
                    i_OpenDestination = i+1;
                    break;
                  }
                }

                if(i_OpenDestination > 0)
                {
                  if (towers[before-1][moveDisc_i] > towers[after-1][i_OpenDestination-1])
                  {
                    error = error + "Move Error: ";
                    error = error + destinationTower;
                    error = error + " has a smaller disc than" + towers[before-1][moveDisc_i] + " on the top";
                    System.out.println(error);
                    System.out.println("");
                    break;
                  }
                }

                usedTowers[before-1] = true;
                usedTowers[after-1] = true;

                towers[before-1][moveDisc_i] = 0;
                towers[after-1][i_OpenDestination] = disc;

                System.out.println("After the move: ");
                String sourceTowerB = "Sourse tower ";
                sourceTowerB = sourceTowerB + before + ": [";
                for (int f = 0; f < n; f++)
                {
                  if (towers[before-1][f] !=0)
                  {
                    sourceTowerB = sourceTowerB + (towers[before-1][f] + ", ");
                  }
                }
                sourceTowerB.trim();
                if (sourceTowerB.contains(","))
                {
                  sourceTowerB = sourceTowerB.substring(0, sourceTowerB.length()-2);
                }
                sourceTowerB = sourceTowerB + "]";
                System.out.println(sourceTowerB);

                String destinationTowerB = "Destination tower ";

                destinationTowerB = destinationTowerB + after + ": [";
                for (int f = 0; f<n; f++)
                {
                  if (towers[after-1][f] !=0)
                  {
                    destinationTowerB = destinationTowerB + (towers[after-1][f] + ", ");

                  }
                }
                destinationTowerB.trim();
                if (destinationTowerB.contains(","))
                {
                  destinationTowerB = destinationTowerB.substring(0, destinationTowerB.length()-2);
                }
                destinationTowerB = destinationTowerB + "]";
                System.out.println(destinationTowerB);

                System.out.println("");
                duration = duration + 6;

              }

              System.out.println("The status of all the towers at the end is as follows:");
              for (int i = 0; i<t; i++)
              {
                currentTower = ("Tower " + (i+1) + ": [");
                for (int f = 0; f<n; f++)
                {
                  currentTower = currentTower + (towers[i][f] + ", ");
                }
                if ((i+1) !=d)
                {
                  if (towers[i][0] != 0)
                  {
                    i = i + 1;
                    error = "Error: Incomplete solution. Tower " + (i) + " is not empty";
                    System.out.println(error);
                    i = i - 1;
                  }
                }
                currentTower.trim();
                currentTower = currentTower.substring(0, currentTower.length()-2);

                currentTower = currentTower + "]";
                System.out.println(currentTower);



              }

              for (int i = 0; i< usedTowers.length; i++)
              {
                if (usedTowers[i])
                {

                }
                else {
                  i = i+1;
                  error = "Error: Tower " + i + " has not been used";
                  System.out.println(error);
                  break;
                }
              }

              System.out.println("");
              if (error == "")
              {
                System.out.println("The sequence of moves is correct.");
              }
              else
              {
                System.out.println("The sequence of moves in incorrect.");
              }

              input_file.close();




            } catch (IOException e) {
              e.printStackTrace();
            }

            System.out.printf("\n");
            return;




        }
}



public class Percolation
{
    private static final int TOP = -1;

    private boolean percolate;
    private Integer [][] grid;
    private final int size; // size of grid
    private int openSites;


    public Percolation(int size) {

        if (size <= 0)
            throw new IllegalArgumentException();

        this.size = size;
        grid = new Integer[size][size]; // Default values are NULL
        this.openSites = 0;
        this.percolate = false;
    }

    private int  findRoot(int i, int j) {

        // while element in the position is not according to its position
        // or in other word element is not a root
        while (grid[i][j] !=  ((i*size)+ j))
        {
            int data = grid[i][j];

            // if data inside position is pointing toward top
            if (data == -1)
                return TOP;
            // else if (data == -2)
            //     return bottom;




            // we can find position of element by this data is the value at
            // position (i,j). if not root then data point to the position of next site
            j =  data % size;
            i = (data - j) / size;
        }

        // return data of root element
        return grid[i][j];
    }





    // connect takes position of two adjacent sites who we want to connect (row, col) and (row2 ,col2)
    private void connect(int row, int col, int row2, int col2)
    {
        // edge points where error can occur
        if (row2 < 0 || col2 < 0 || row2 >= grid.length || col2 >= grid.length || grid[row2][col2] == null) {
            return;
        }
        int rootA = findRoot(row, col);
        int rootB = findRoot(row2, col2);
        if (rootA ==  rootB)
        {
            return;               // if both elements have same root nothing need to do
        }
        if (rootA == TOP)
        {
            int j = rootB % size;
            int i = (rootB - j) / size;
            grid[i][j] = TOP;
            if (i == size- 1)
            {
                percolate = true;                           // PERCOLATION  CONDITION
            }
        }
        else if (rootB == TOP)
        {
            int j = rootA % size;
            int i = (rootA - j) / size;

            grid[i][j] = TOP;
            if (i == size - 1)
            {
                percolate = true;                         // PERCOLATION  CONDITION
            }
        }
        else if (rootA > rootB) {
            // position of rootB i.e (i,j)
            int j = rootB % size;
            int i = (rootB - j) / size;
            //  rootB will point toward the site (row,col) whose root value is greater

            grid[i][j] = (row*size + col);
        }
        else  {
            // like previous one
            int j = rootA % size;
            int i = (rootA - j) / size;

            grid[i][j] = (row2*size + col2);
        }
    }



    public void open(int row, int col) {

        if (row <= 0 || col <= 0 || row > size || col > size)
            throw new IllegalArgumentException();

        if (isOpen(row, col))
            return;

        row = row - 1;
        col = col - 1;
        openSites = openSites + 1;

        grid[row][col] = (row*size) +  col;

        if (row == 0)              // if row of site is on top of grid
            grid[row][col]  = TOP; // then its connected to common top

        int upRow = row - 1;   // adjacent top row
        int downRow = row + 1; // adjacent bottom row

        connect(row, col, upRow, col); // connect to upper site
        connect(row, col, downRow, col); // connect to bottom site

        int leftCol = col - 1;        //  adjacent left column
        int rightCol = col + 1;       // adjacent right column

        connect(row, col, row, leftCol);  // connect to left site
        connect(row, col, row, rightCol); // connect to right site
    }

    public boolean isOpen(int row, int col) {

        if (row <= 0 || col <= 0 || row > size || col > size)
            throw new IllegalArgumentException();

        row = row - 1;
        col = col - 1;
        if (grid[row][col] == null)
        {
            return false;
        }
        return true;
    }

    public boolean isFull(int row, int col) {
        if (row <= 0 || col <= 0 || row > size || col > size)
            throw new IllegalArgumentException();

        row = row - 1;
        col = col - 1;


        if (grid[row][col] != null && findRoot(row, col) == TOP) {
            return true;
        }
        return false;
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return percolate;
    }


}



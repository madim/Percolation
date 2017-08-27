package percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int count;
    private int N;
    private int openSites[];
    private WeightedQuickUnionUF uf;

    public Percolation(int n) {
        count = n * n;
        N = n;
        openSites = new int[n * n];
        uf = new WeightedQuickUnionUF(count + 2);
    }

    public void open(int row, int col) {
        validate(row, col);
        if (isOpen(row, col)) return;
        int currentSite = xyTo1D(row, col);
        openSites[currentSite] = 1;
        connectTopSite(row, col, currentSite);
        connectLeftSite(row, col, currentSite);
        connectRightSite(row, col, currentSite);
        connectBottomSite(row, col, currentSite);
    }

    private void connectLeftSite(int row, int col, int currentSite) {
        if (col == 1) return;
        int leftSite = xyTo1D(row, col - 1);
        if (isOpen(row, col - 1)) {
            uf.union(currentSite, leftSite);
            openSites[leftSite] = 1;
        }
    }

    private void connectRightSite(int row, int col, int currentSite) {
        if (col == N) return;
        int rightSite = xyTo1D(row, col + 1);
        if (isOpen(row, col + 1)) {
            uf.union(currentSite, rightSite);
            openSites[rightSite] = 1;
        }
    }

    private void connectTopSite(int row, int col, int currentSite) {
        if (row == 1 && isOpen(row, col)) {
            uf.union(0, currentSite);
            return;
        }
        int topSite = xyTo1D(row - 1, col);
        if (isOpen(row - 1, col)) {
            uf.union(currentSite, topSite);
            openSites[topSite] = 1;
        }
    }

    private void connectBottomSite(int row, int col, int currentSite) {
        if (row == N &&  isOpen(row, col)) {
            uf.union(count + 1, currentSite);
            return;
        }
        int bottomSite = xyTo1D(row + 1, col);
        if (isOpen(row + 1, col)) {
            uf.union(currentSite, bottomSite);
            openSites[bottomSite] = 1;
        }
    }

    private int xyTo1D(int row, int col) {
        validate(row, col);
        return (row - 1) * N + (col - 1);
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return openSites[xyTo1D(row, col)] == 1;
    }

    public boolean isFull(int row, int col) {
        if (!isOpen(row, col)) return false;
        int currentSite = xyTo1D(row, col);
        return uf.connected(0, currentSite);
    }

    public int numberOfOpenSites() {
        int count = 0;
        for (int i = 0; i < openSites.length; i++) {
            if (openSites[i] == 1) count++;
        }
        return count;
    }

    public boolean percolates() {
        return uf.connected(0, count + 1);
    }

    private void validate(int row, int col) {
        if ((row <= 0 || row > count) || (col <= 0 || col > count))
            throw new IndexOutOfBoundsException("Argument is outside its prescribed range, " + row + " : " + col);
    }

    public static void main(String[] args) {
    }
}

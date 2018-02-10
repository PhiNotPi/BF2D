
public class Grid extends Util {

  public static void main(String[] args) {
    runTest(s2c("[-]"), s2c(">"));
  }

  public static void runTest(Cmd[] xprog, Cmd[] yprog) {
    Grid g = new Grid();
    Node init = new Node();
    init.xprog = xprog;
    init.yprog = yprog;
    g.genGrid(5, 5, init);
    System.out.println("test 1:");
    Util.printCharArr(g.formatPrint());
    System.out.println();
    init.tape = new int[] { 1, 0, 0 };
    g.genGrid(5, 5, init);
    System.out.println("test 2:");
    Util.printCharArr(g.formatPrint());
  }

  public static void runBruteForce() {
    ProgGen2D.run();
    Grid g = new Grid();
    for (int a = 0; a < 1000; a++) {
      for (int b = 0; b <= a / 2; b++) {
        int i = a - b;
        int j = b;
        Node init = new Node();
        init.xprog = ProgGen2D.condensed.get(i);
        init.yprog = ProgGen2D.condensed.get(j);
        Node res = g.genGrid(5, 5, init);
        // System.out.println(i + " " + j + " " + res.tape[0]);
        // Util.printCharArr(g.formatPrint());
        if (res.tape[0] == 0) {
          init.tape = new int[] { 1, 0, 0 };
          Node res2 = g.genGrid(5, 5, init);
          if (res2.tape[0] == 0) {
            System.out.println(i + " " + j + " " + Util.c2s(init.xprog) + " "
                + Util.c2s(init.yprog));
          }
        }
      }
    }
  }

  Node[][] nodes;

  public Node genGrid(int xtime, int ytime, Node init) {
    nodes = new Node[xtime + 1][ytime + 1];
    for (int x = 0; x <= xtime; x++) {
      for (int y = 0; y <= ytime; y++) {
        if (x == 0 && y == 0) {
          nodes[x][y] = init;
        } else if (x == 0) {
          nodes[x][y] = Node.calculateNode(null, nodes[x][y - 1], null);
        } else if (y == 0) {
          nodes[x][y] = Node.calculateNode(nodes[x - 1][y], null, null);
        } else {
          nodes[x][y] = Node.calculateNode(nodes[x - 1][y], nodes[x][y - 1],
              nodes[x - 1][y - 1]);
        }
      }
    }
    return nodes[xtime][ytime];
  }

  public char[][] formatPrint() {
    char[][] res = new char[nodes[0].length * Node.subprintrows][nodes.length
        * Node.subprintcols];
    for (int x = 0; x < nodes.length; x++) {
      for (int y = 0; y < nodes[0].length; y++) {
        int bigrow = Node.subprintrows * (nodes[0].length - 1 - y);
        int bigcol = Node.subprintcols * x;
        char[][] subprint = nodes[x][y].formatprint();
        for (int r = 0; r < subprint.length; r++) {
          for (int c = 0; c < subprint[0].length; c++) {
            res[bigrow + r][bigcol + c] = subprint[r][c];
          }
        }
      }
    }
    return res;
  }
}

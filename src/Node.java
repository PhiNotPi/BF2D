
public class Node extends Util {

  public static void main(String[] args) {
    Node n = new Node();
    printCharArr(n.formatprint());
  }

  static int subprintrows = 4;
  static int subprintcols = 13;
  Cmd[] xprog = { Cmd.R, Cmd.I };
  Cmd[] yprog = { Cmd.I, Cmd.R };
  int xip = 0;
  int yip = 0;
  int[] tape = { 0, 0, 0 };
  int tp = 0;

  static String numbers = "0123456789abcdefghijklmnopqrstuvwxyz";

  public char[] tape2char(int loc) {
    char[] res = new char[2];
    if (tape[loc] >= 0) {
      res[0] = ' ';
    } else {
      res[0] = '-';
    }
    int abs = Math.abs(tape[loc]);
    if (abs < numbers.length()) {
      res[1] = numbers.charAt(abs);
    } else {
      res[1] = '?';
    }
    return res;
  }

  public char[][] formatprint() {
    char[][] res = new char[subprintrows][subprintcols];
    for (int i = 0; i < res.length; i++) {
      for (int j = 0; j < res[0].length; j++) {
        if (j == 0 && i == 3) {
          res[i][j] = '+';
        } else if (j == 0) {
          res[i][j] = '|';
        } else if (i == 3) {
          res[i][j] = '-';
        } else {
          res[i][j] = ' ';
        }
      }
    }
    // res[0][0] = 'y';
    // res[subprintrows - 1][subprintcols - 1] = 'x';

    int tapepos = 1;
    int taperow = 0;
    for (int k = 0; k < tape.length; k++) {
      if (k == tp) {
        res[taperow][tapepos++] = '(';
      } else {
        res[taperow][tapepos++] = ' ';
      }
      char[] cell = tape2char(k);
      res[taperow][tapepos++] = cell[0];
      res[taperow][tapepos++] = cell[1];
      if (k == tp) {
        res[taperow][tapepos++] = ')';
      } else {
        res[taperow][tapepos++] = ' ';
      }
    }
    int xprogpos = 1;
    int xprogrow = 1;
    for (int k = 0; k < xprog.length && xprog[k] != null; k++) {
      if (k == xip) {
        res[xprogrow][xprogpos++] = '(';
      }
      res[xprogrow][xprogpos++] = xprog[k].c;
      if (k == xip) {
        res[xprogrow][xprogpos++] = ')';
      }
    }
    int yprogpos = 1;
    int yprogrow = 2;
    for (int k = 0; k < yprog.length && yprog[k] != null; k++) {
      if (k == yip) {
        res[yprogrow][yprogpos++] = '(';
      }
      res[yprogrow][yprogpos++] = yprog[k].c;
      if (k == yip) {
        res[yprogrow][yprogpos++] = ')';
      }
    }
    return res;
  }

  public Node clone() {
    Node res = new Node();
    res.tape = tape.clone();
    res.tp = tp;
    res.xprog = xprog.clone();
    res.yprog = yprog.clone();
    res.xip = xip;
    res.yip = yip;
    return res;
  }

  public static Node evalX(Node n) {
    Node res = n.clone();
    if (res.xip >= res.xprog.length || res.xprog[res.xip] == null) {
      return res;
    }
    switch (res.xprog[res.xip]) {
    case I:
      res.tape[res.tp]++;
      break;
    case D:
      res.tape[res.tp]--;
      break;
    case R:
      res.tp = (res.tp + 1) % res.tape.length;
      break;
    case L:
      res.tp = (res.tp - 1 + res.tape.length) % res.tape.length;
      break;
    case W:
      if (res.tape[res.tp] == 0) {
        while (res.xprog[res.xip] != Cmd.E) {
          res.xip++;
        }
      }
      break;
    case E:
      if (res.tape[res.tp] != 0) {
        while (res.xprog[res.xip] != Cmd.W) {
          res.xip--;
        }
      }
      break;
    }
    res.xip++;
    return res;
  }

  public static Node evalY(Node n) {
    Node res = n.clone();
    if (res.yip >= res.yprog.length || res.yprog[res.yip] == null) {
      return res;
    }
    switch (res.yprog[res.yip]) {
    case I:
      res.tape[res.tp]++;
      break;
    case D:
      res.tape[res.tp]--;
      break;
    case R:
      res.tp = (res.tp + 1) % res.tape.length;
      break;
    case L:
      res.tp = (res.tp - 1 + res.tape.length) % res.tape.length;
      break;
    case W:
      if (res.tape[res.tp] == 0) {
        while (res.yprog[res.yip] != Cmd.E) {
          res.yip++;
        }
      }
      break;
    case E:
      if (res.tape[res.tp] != 0) {
        while (res.yprog[res.yip] != Cmd.W) {
          res.yip--;
        }
      }
      break;
    }
    res.yip++;
    return res;
  }

  public static Node calculateNode(Node x, Node y, Node xy) {
    Node res = new Node();
    if (xy != null) {
      res.xprog = xy.xprog.clone();
      res.yprog = xy.yprog.clone();
      res.tape = xy.tape.clone();
      x = evalX(x);
      y = evalY(y);
      res.xip = x.xip;
      res.yip = y.yip;
      res.tp = (x.tp + y.tp - xy.tp + res.tape.length) % res.tape.length;
      for (int i = 0; i < res.tape.length; i++) {
        res.tape[i] = x.tape[i] + y.tape[i] - xy.tape[i];
      }
    } else if (x != null) {
      x = evalX(x);
      res.xprog = x.xprog.clone();
      res.yprog = x.yprog.clone();
      res.tape = x.tape.clone();
      res.xip = x.xip;
      res.yip = x.yip;
      res.tp = x.tp;
    } else if (y != null) {
      y = evalY(y);
      res.xprog = y.xprog.clone();
      res.yprog = y.yprog.clone();
      res.tape = y.tape.clone();
      res.xip = y.xip;
      res.yip = y.yip;
      res.tp = y.tp;
    }
    return res;
  }

}


public enum Cmd {
  L('<'), R('>'), I('+'), D('-'), W('['), E(']');
  public char c;

  Cmd(char c) {
    this.c = c;
  };

  public String toString() {
    return Character.toString(c);
  }

}

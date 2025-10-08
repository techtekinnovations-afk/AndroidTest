package androidx.constraintlayout.core.parser;

import androidx.constraintlayout.widget.ConstraintLayout;

public class CLParser {
    static boolean sDebug = false;
    private String mContent;
    private boolean mHasComment = false;
    private int mLineNumber;

    enum TYPE {
        UNKNOWN,
        OBJECT,
        ARRAY,
        NUMBER,
        STRING,
        KEY,
        TOKEN
    }

    public static CLObject parse(String string) throws CLParsingException {
        return new CLParser(string).parse();
    }

    public CLParser(String content) {
        this.mContent = content;
    }

    public CLObject parse() throws CLParsingException {
        int i;
        int startIndex;
        int i2;
        char ck;
        char[] content = this.mContent.toCharArray();
        int length = content.length;
        int i3 = 1;
        this.mLineNumber = 1;
        int startIndex2 = -1;
        int i4 = 0;
        while (true) {
            if (i4 >= length) {
                break;
            }
            char c = content[i4];
            if (c == '{') {
                startIndex2 = i4;
                break;
            }
            if (c == 10) {
                this.mLineNumber++;
            }
            i4++;
        }
        if (startIndex2 != -1) {
            CLObject root = CLObject.allocate(content);
            root.setLine(this.mLineNumber);
            root.setStart((long) startIndex2);
            CLElement currentElement = root;
            int i5 = startIndex2 + 1;
            while (true) {
                if (i5 >= length) {
                    i = i3;
                    int i6 = startIndex2;
                    break;
                }
                char c2 = content[i5];
                if (c2 == 10) {
                    this.mLineNumber += i3;
                }
                if (this.mHasComment) {
                    if (c2 == 10) {
                        this.mHasComment = false;
                    } else {
                        i2 = i3;
                        startIndex = startIndex2;
                        i5++;
                        i3 = i2;
                        startIndex2 = startIndex;
                    }
                }
                if (currentElement == null) {
                    i = i3;
                    int i7 = startIndex2;
                    break;
                }
                if (currentElement.isDone()) {
                    currentElement = getNextJsonElement(i5, c2, currentElement, content);
                    i2 = i3;
                    startIndex = startIndex2;
                } else if (currentElement instanceof CLObject) {
                    if (c2 == '}') {
                        currentElement.setEnd((long) (i5 - 1));
                        i2 = i3;
                        startIndex = startIndex2;
                    } else {
                        currentElement = getNextJsonElement(i5, c2, currentElement, content);
                        i2 = i3;
                        startIndex = startIndex2;
                    }
                } else if (currentElement instanceof CLArray) {
                    if (c2 == ']') {
                        currentElement.setEnd((long) (i5 - 1));
                        i2 = i3;
                        startIndex = startIndex2;
                    } else {
                        currentElement = getNextJsonElement(i5, c2, currentElement, content);
                        i2 = i3;
                        startIndex = startIndex2;
                    }
                } else if (currentElement instanceof CLString) {
                    if (content[(int) currentElement.mStart] == c2) {
                        currentElement.setStart(currentElement.mStart + 1);
                        currentElement.setEnd((long) (i5 - 1));
                    }
                    i2 = i3;
                    startIndex = startIndex2;
                } else {
                    if (currentElement instanceof CLToken) {
                        CLToken token = (CLToken) currentElement;
                        i2 = i3;
                        startIndex = startIndex2;
                        if (!token.validate(c2, (long) i5)) {
                            throw new CLParsingException("parsing incorrect token " + token.content() + " at line " + this.mLineNumber, token);
                        }
                    } else {
                        i2 = i3;
                        startIndex = startIndex2;
                    }
                    if (((currentElement instanceof CLKey) || (currentElement instanceof CLString)) && (((ck = content[(int) currentElement.mStart]) == '\'' || ck == '\"') && ck == c2)) {
                        currentElement.setStart(currentElement.mStart + 1);
                        currentElement.setEnd((long) (i5 - 1));
                    }
                    if (currentElement.isDone() == 0 && (c2 == '}' || c2 == ']' || c2 == ',' || c2 == ' ' || c2 == 9 || c2 == 13 || c2 == 10 || c2 == ':')) {
                        currentElement.setEnd((long) (i5 - 1));
                        if (c2 == '}' || c2 == ']') {
                            currentElement = currentElement.getContainer();
                            currentElement.setEnd((long) (i5 - 1));
                            if (currentElement instanceof CLKey) {
                                currentElement = currentElement.getContainer();
                                currentElement.setEnd((long) (i5 - 1));
                            }
                        }
                    }
                }
                if (currentElement.isDone() && (!(currentElement instanceof CLKey) || ((CLKey) currentElement).mElements.size() > 0)) {
                    currentElement = currentElement.getContainer();
                }
                i5++;
                i3 = i2;
                startIndex2 = startIndex;
            }
            while (currentElement != null && !currentElement.isDone()) {
                if (currentElement instanceof CLString) {
                    currentElement.setStart((long) (((int) currentElement.mStart) + i));
                }
                currentElement.setEnd((long) (length - 1));
                currentElement = currentElement.getContainer();
            }
            if (sDebug) {
                System.out.println("Root: " + root.toJSON());
            }
            return root;
        }
        throw new CLParsingException("invalid json content", (CLElement) null);
    }

    private CLElement getNextJsonElement(int position, char c, CLElement currentElement, char[] content) throws CLParsingException {
        CLElement currentElement2;
        switch (c) {
            case 9:
            case 10:
            case 13:
            case ' ':
            case ',':
            case ':':
                currentElement2 = currentElement;
                char[] cArr = content;
                break;
            case '\"':
            case '\'':
                int position2 = position;
                CLElement currentElement3 = currentElement;
                char[] content2 = content;
                if ((currentElement3 instanceof CLObject) != 0) {
                    return createElement(currentElement3, position2, TYPE.KEY, true, content2);
                }
                return createElement(currentElement3, position2, TYPE.STRING, true, content2);
            case '+':
            case '-':
            case '.':
            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE /*48*/:
            case '1':
            case '2':
            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_TAG /*51*/:
            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_BASELINE_TO_TOP_OF /*52*/:
            case '5':
            case ConstraintLayout.LayoutParams.Table.LAYOUT_MARGIN_BASELINE /*54*/:
            case '7':
            case '8':
            case '9':
                return createElement(currentElement, position, TYPE.NUMBER, true, content);
            case '/':
                int position3 = position;
                currentElement2 = currentElement;
                char[] content3 = content;
                if (position3 + 1 < content3.length && content3[position3 + 1] == '/') {
                    this.mHasComment = true;
                    break;
                }
            case '[':
                return createElement(currentElement, position, TYPE.ARRAY, true, content);
            case ']':
            case '}':
                int position4 = position;
                CLElement currentElement4 = currentElement;
                char[] cArr2 = content;
                currentElement4.setEnd((long) (position4 - 1));
                CLElement currentElement5 = currentElement4.getContainer();
                currentElement5.setEnd((long) position4);
                return currentElement5;
            case '{':
                return createElement(currentElement, position, TYPE.OBJECT, true, content);
            default:
                int position5 = position;
                CLElement currentElement6 = currentElement;
                char[] content4 = content;
                if ((currentElement6 instanceof CLContainer) == 0 || (currentElement6 instanceof CLObject)) {
                    return createElement(currentElement6, position5, TYPE.KEY, true, content4);
                }
                CLElement currentElement7 = createElement(currentElement6, position5, TYPE.TOKEN, true, content4);
                CLToken token = (CLToken) currentElement7;
                if (token.validate(c, (long) position5)) {
                    return currentElement7;
                }
                throw new CLParsingException("incorrect token <" + c + "> at line " + this.mLineNumber, token);
        }
        return currentElement2;
    }

    private CLElement createElement(CLElement currentElement, int position, TYPE type, boolean applyStart, char[] content) {
        CLElement newElement = null;
        if (sDebug) {
            System.out.println("CREATE " + type + " at " + content[position]);
        }
        switch (type.ordinal()) {
            case 1:
                newElement = CLObject.allocate(content);
                position++;
                break;
            case 2:
                newElement = CLArray.allocate(content);
                position++;
                break;
            case 3:
                newElement = CLNumber.allocate(content);
                break;
            case 4:
                newElement = CLString.allocate(content);
                break;
            case 5:
                newElement = CLKey.allocate(content);
                break;
            case 6:
                newElement = CLToken.allocate(content);
                break;
        }
        if (newElement == null) {
            return null;
        }
        newElement.setLine(this.mLineNumber);
        if (applyStart) {
            newElement.setStart((long) position);
        }
        if (currentElement instanceof CLContainer) {
            newElement.setContainer((CLContainer) currentElement);
        }
        return newElement;
    }
}

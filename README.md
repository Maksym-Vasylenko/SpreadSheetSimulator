# SpreadSheetSimulator

SpreadsheetSimulator

Here is the task:

Need to develop a simple batch spreadsheet processor. It must be able to process cells just like in a ordinary spreadsheet, but will use simplified expressions. Each cell may contain:

 - nothing.
 - integer or float number;
 - text labels, which is started with ' symbol.
 - Expression, which is started with '=' symbol and may contain contain numbers, cell references (fig. 1), and simple arithmetic operations ('+', '-', '*', '/') with cell references.
These restrictions has been made in order to simplify parsing, because parsing implementation is not important part of these problem. You should safely rely on these restrictions. Here is the grammar for cells content:

Sheet processing rules are:

- all expressions should be substituted with calculated results.
- all calculations should be taken with signed integer arithmetic.
- text cells should be evaluated to corresponding text without ' prefix.
- there are no operations on text labels allowed.
- in case of any error in cell evaluation resulting cell must contain word with error description started with ‘#’ symbol.
Program should take sheet description with formulas from the standard input, evaluate it, and print resulting table to the standard output. Input data represented as tabulation-separated table.

Sample Input:

12  =C2 3 'Sample

=A1+B1*C1/5 =A2*B1 =B3-C3 'Spread

'Test =4-3 5 'Sheet 

Sample Output:

12 -4 3  Sample

9.6 -38.4 -4 Spread

Test 1 5  Sheet  


public class lexicalAnalyser {

	public lexicalAnalyser()
	{

	}
	//Removes spaces and Tabs
	public String removeChars(String input)
	{
		input=input.replaceAll(" ", "");
		input=input.replaceAll("\t", "");
		return input;
	}
	//Removes Comments
	public String removeComments(String input)
	{
		char[]a;
		a=input.toCharArray();
		int i=0;
		int start=0;
		int end=0;
		int comments =0;
		while (i<a.length)
		{
			if(a[i]=='*'&& comments==0)
			{
				if(a[i-1]=='/')
				{
				start=(i-1);
				comments=1;
				}
			}
			if(a[i]=='/'&& comments!=0)
			{
				if(a[i-1]=='*')
				{
				end=(i);
				sectionToSpaces(a,start,end);
				comments=0;
				
				}
			}
			i++;
		}
		//System.out.println(start);
		//System.out.println(end);
		if(comments!=0)
		{
			System.out.println("ERROR.Comments never end.");
		}
		String output =new String(a);
		return output;
	}
	//Can remove a subsection
	public char[] sectionToSpaces(char[] input,int start, int end)
	{
		for(int i=start;i<=end;i++)
		{
			input[i]=' ';
		}
		return input;
	}
	//Removes comments and spaces.
	public String processString(String input)
		{
		input=removeComments(input);
		input=removeChars(input);
		
	return input;
		}
	//Checks to make sure String doesn't include any chars not in the array.
public boolean isvalidString(String input, char[] symbols)
	{
	
	char[]b = input.toCharArray();
	int i=0;
	boolean statement=true;
	while(i<b.length)
	{
	boolean symbol=false;
	for(int j=0;j<symbols.length;j++)
	{
		if(b[i]==symbols[j])
		{
			symbol=true;
		}
	}
	if(symbol==false)
	{
	statement=false;
	i=b.length;
	}
	else
	{
		i++;
	}
	}
	return statement;
	}
}

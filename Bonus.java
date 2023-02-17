import java.util.*;
public class Bonus {
    public static int profit=0;
    public static List<Integer>list1=new ArrayList<>();
    public static List<Integer>ans1=new ArrayList<>();
    
    public static void find(int stock[][],int c,int index,List<Integer>temp,List<Integer>tempstock,int p)
    {
        int m=stock.length,n=stock[0].length;
        if(index>=n)
        {
            if(temp.size()%2==0 && p>profit)
            {
                profit=p;
                ans1.clear();
                list1.clear();
                for(int i=0;i<temp.size();i++)
                {
                    list1.add(temp.get(i));
                    ans1.add(tempstock.get(i));
                }
            }
            return;
        }
        for(int j=index+1;j<n;j++)
        {
            int max=-10000000,maxindex=-1;
            for(int k=1;k<m;k++)
            {
                if(max<stock[k][j]-stock[k][index])
                {
                    max=stock[k][j]-stock[k][index];
                    maxindex=k;
                }
            }
            temp.add(index);
            temp.add(j);
            tempstock.add(maxindex);
            tempstock.add(maxindex);
            find(stock,c,j+c+1,temp,tempstock,p+max);
            temp.remove(temp.size()-1);
            temp.remove(temp.size()-1);
            tempstock.remove(tempstock.size()-1);
            tempstock.remove(tempstock.size()-1);
        }
        find(stock,c,index+1,temp,tempstock,p);
        return;
    }
    public static void A7(int stock[][],int c)
    {
        List<Integer>temp=new ArrayList<>();
        List<Integer>tempstock=new ArrayList<>();
        find(stock,c,1,temp,tempstock,0);
        for(int i=1;i<list1.size();i=i+2)
        {
            System.out.println(""+ans1.get(i)+" "+list1.get(i-1)+" "+list1.get(i));
        }
    }
    public static void A8(int stock[][],int c)
    {
        int m=stock.length,n=stock[0].length;
        int dp[][]=new int[m][n];
        for(int i=2;i<n;i++)
        {
            for(int j=1;j<m;j++)
            {
                for(int p=1;p<i;p++)
                {
                    dp[j][i]=Math.max(dp[j][i],dp[m-1][Math.max(p-c-1,0)]+stock[j][i]-stock[j][p]);
                    dp[j][i]=Math.max(dp[j][i],Math.max(dp[j-1][i],dp[j][i-1]));
                }
            }
        }
        System.out.println(""+dp[m-1][n-1]);
        List<Integer>list=new ArrayList<>();
        List<Integer>ans=new ArrayList<>();
        int index=n-1,s=m-1;
        while(index>0 && s>0)
        {
            if(dp[s][index]==dp[s][index-1])
                index--;
            else
            {
                if(dp[s][index]==dp[s-1][index])
                    s--;
                else
                {
                    for(int i=1;i<index;i++)
                    {
                        if(dp[s][index]==stock[s][index]-stock[s][i]+dp[m-1][Math.max(i-c-1,0)])
                        {
                            list.add(i);
                            list.add(index);
                            ans.add(s);
                            ans.add(s);
                            index=Math.max(i-c-1,0);
                            s=m-1;
                            break;
                        }
                    }
                }
            }
        }
        for(int i=list.size()-2;i>=0;i=i-2)
        {
            System.out.println(""+ans.get(i)+" "+list.get(i)+" "+list.get(i+1));
        }
    }

    public static int find(int stock[][],int c,int index,int dp[])
    {
        int m=stock.length,n=stock[0].length;
        if(index<=0)
            return 0;
        if(dp[index]!=-1)
        {
            return dp[index];
        }
        int ans=-100000;
        for(int j=index-1;j>=1;j--)
        {
            int max=-100000;
            for(int k=1;k<m;k++)
            {
                max=Math.max(max,stock[k][index]-stock[k][j]);
            }
            ans=Math.max(ans,max+find(stock,c,j-c-1,dp));
        }
        ans=Math.max(ans,find(stock,c,index-1,dp));
        return dp[index]=ans;
    }
    public static int find1(int stock[][],int c,int index,int dp[],int p,List<Integer>list,List<Integer>ans)
    {
        int m=stock.length,n=stock[0].length;
        if(index<=0 || p==0)
        {
            if(p==0)
            {
                for(int i=list.size()-2;i>=0;i=i-2)
                {
                    System.out.println(""+ans.get(i)+" "+list.get(i)+" "+list.get(i+1));
                }
                return 1;
            }
            return 0;
        }
        if(dp[index]!=p)
            return 0;
        for(int j=1;j<index;j++)
        {
            int max=-1000000,maxindex=-1;
            for(int k=1;k<m;k++)
            {
                if(max<stock[k][index]-stock[k][j])
                {
                    max=stock[k][index]-stock[k][j];
                    maxindex=k;
                }
            }
            list.add(j);
            list.add(index);
            ans.add(maxindex);
            ans.add(maxindex);
            if(find1(stock,c,j-c-1,dp,p-max,list,ans)==1)
                return 1;
            list.remove(list.size()-1);
            list.remove(list.size()-1);
            ans.remove(ans.size()-1);
            ans.remove(ans.size()-1);
        }
        if(find1(stock,c,index-1,dp,p,list,ans)==1)
            return 1;
        return 0;
    }
    public static void A9Memo(int stock[][],int c)
    {
        int m=stock.length,n=stock[0].length;
        int dp[]=new int[n];
        for(int j=0;j<n;j++)
        {
            dp[j]=-1;
        }
        int x=find(stock,c,n-1,dp);
        System.out.println(""+x);
        List<Integer>list=new ArrayList<>();
        List<Integer>ans=new ArrayList<>();
        find1(stock,c,n-1,dp,x,list,ans);
    }


    public static void A9Bottom(int stock[][],int c)
    {
        int m=stock.length,n=stock[0].length;
        int dp[][]=new int[m][n];
        int prevdiff[][]=new int[m][n];
        for(int i=1;i<m;i++)
        {
            prevdiff[i][1]=-stock[i][1];
        }
        for(int i=2;i<n;i++)
        {
            for(int j=1;j<m;j++)
            {
                dp[j][i]=Math.max(dp[j][i],Math.max(dp[j][i-1],dp[j-1][i]));
                dp[j][i]=Math.max(dp[j][i],stock[j][i]+prevdiff[j][i-1]);
                prevdiff[j][i]=Math.max(prevdiff[j][i-1],dp[m-1][Math.max(i-c-1,0)]-stock[j][i]);
            }
        }
        System.out.println(""+dp[m-1][n-1]);
        List<Integer>list=new ArrayList<>();
        List<Integer>ans=new ArrayList<>();
        int index=n-1,s=m-1;
        while(index>0 && s>0)
        {
            if(dp[s][index]==dp[s][index-1])
                index--;
            else
            {
                if(dp[s][index]==dp[s-1][index])
                    s--;
                else
                {
                    for(int i=1;i<index;i++)
                    {
                        if(dp[s][index]==stock[s][index]-stock[s][i]+dp[m-1][Math.max(i-c-1,0)])
                        {
                            list.add(i);
                            list.add(index);
                            ans.add(s);
                            ans.add(s);
                            index=Math.max(i-c-1,0);
                            s=m-1;
                            break;
                        }
                    }
                }
            }
        }
        for(int i=list.size()-2;i>=0;i=i-2)
        {
            System.out.println(""+ans.get(i)+" "+list.get(i)+" "+list.get(i+1));
        }
    }

    public static void main(String args[])
    {
        Scanner sc=new Scanner(System.in);
        int c=sc.nextInt();
        int m=sc.nextInt();
        int n=sc.nextInt();
        int stock[][]=new int[m+1][n+1];
        for(int i=1;i<=m;i++)
        {
            for(int j=1;j<=n;j++)
            {
                stock[i][j]=(int) (Math.random()*(1000-1+1))+1;
               // stock[i][j]=sc.nextInt();
            }
        }
        sc.close();
        long startTime,endTime,totalTime;
        System.out.println(" ");
        System.out.println("A7 ");
        startTime=System.currentTimeMillis();
        A7(stock,c);
        endTime   = System.currentTimeMillis();
        totalTime = endTime - startTime;
        System.out.println(""+totalTime);

        System.out.println("");
        System.out.println("A8 ");
        startTime=System.currentTimeMillis();
        A8(stock,c);
        endTime   = System.currentTimeMillis();
        totalTime = endTime - startTime;
        System.out.println(""+totalTime);

        System.out.println("");
        System.out.println("A9Bottom ");
        startTime=System.currentTimeMillis();
        A9Bottom(stock,c);
        endTime   = System.currentTimeMillis();
        totalTime = endTime - startTime;
        System.out.println(""+totalTime);

        System.out.println("");
        System.out.println("A9Memo");
        startTime=System.currentTimeMillis();
        A9Memo(stock,c);
        endTime   = System.currentTimeMillis();
        totalTime = endTime - startTime;
        System.out.println(""+totalTime);
    }
}

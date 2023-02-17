import java.util.*;
public class Main_final
{
    public static int profit4=0;
    public static List<Integer>list1=new ArrayList<>();
    public static List<Integer>ans1=new ArrayList<>();
    public static void A1(int stock[][])
    {
        int profit=0,s=1,buy=1,sell=1,m=stock.length,n=stock[0].length;
        for(int i=1;i<m;i++)
        {
            for(int j=1;j<n;j++)
            {
                for(int k=1;k<j;k++)
                {
                    if(profit<stock[i][j]-stock[i][k])
                    {
                        profit=stock[i][j]-stock[i][k];
                        s=i;
                        buy=k;
                        sell=j;
                    }
                }
            }
        }
        System.out.println(""+s+" "+buy+" "+sell);
    }

    public static void A2(int stock[][])
    {
        int profit=0,s=1,buy=1,sell=1,m=stock.length,n=stock[0].length;
        for(int i=1;i<m;i++)
        {
            int min=1;
            for(int j=2;j<n;j++)
            {
                if(profit<stock[i][j]-stock[i][min])
                {
                    profit=stock[i][j]-stock[i][min];
                    s=i;
                    buy=min;
                    sell=j;
                }
                if(stock[i][j]<stock[i][min])
                {
                    min=j;
                }
            }
        }
        /*int ms=stock[s][sell]-stock[s][buy];*/
        System.out.println(""+s+" "+buy+" "+sell);
    }

    public static void find(int stock[],int index,int dp[],int successor[])
    {
        if(index==1)
        {
            successor[index]=index;
            return;
        }
        find(stock,index-1,dp,successor);
        dp[index]=Math.max(0,dp[index-1]+stock[index]-stock[index-1]);
        successor[index]=successor[index-1];
        if(dp[index]==0)
            successor[index]=index;
        return;
    }
    public static void A3Memo(int stock[][])
    {
        int profit=0,s=1,buy=1,sell=1,m=stock.length,n=stock[0].length;
        for(int i=1;i<m;i++)
        {
            int dp[]=new int[n];
            int successor[]=new int[n];
            find(stock[i],n-1,dp,successor);
            for(int j=1;j<n;j++)
            {
                if(profit<dp[j])
                {
                    profit=dp[j];
                    s=i;
                    buy=successor[j];
                    sell=j;
                }
            }
        }
        System.out.println(""+s+" "+buy+" "+sell);
    }

    public static void A3Bottom(int stock[][])
    {
        int profit=0,s=1,buy=1,sell=1,m=stock.length,n=stock[0].length;
        for(int i=1;i<m;i++)
        {
            int dp[]=new int[n];
            int successor=1;
            for(int j=2;j<n;j++)
            {
                dp[j]=Math.max(0,dp[j-1]+stock[i][j]-stock[i][j-1]);
                if(dp[j]==0)
                    successor=j;
                if(profit<dp[j])
                {
                    profit=dp[j];
                    sell=j;
                    buy=successor;
                    s=i;
                }
            }
        }
        /*int ms=stock[s][sell]-stock[s][buy];*/
        System.out.println(""+s+" "+buy+" "+sell);
    }
    
    public static void find(int stock[][],int k,int index,List<Integer>temp,List<Integer>tempstock,int p,int prevstock)
    {
        int m=stock.length,n=stock[0].length;
        if(k==0 || index>=n)
        {
            if(temp.size()%2==0 && p>profit4)
            {
                profit4=p;
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
            int max=-1000000000,maxindex=-1;
            for(int q=1;q<m;q++)
            {
                if(q!=prevstock && max<stock[q][j]-stock[q][index])
                {
                    max=stock[q][j]-stock[q][index];
                    maxindex=q;
                }
            }
            temp.add(index);
            temp.add(j);
            tempstock.add(maxindex);
            tempstock.add(maxindex);
            find(stock,k-1,j,temp,tempstock,p+max,maxindex);
            temp.remove(temp.size()-1);
            temp.remove(temp.size()-1);
            tempstock.remove(tempstock.size()-1);
            tempstock.remove(tempstock.size()-1);
        }
        find(stock,k,index+1,temp,tempstock,p,-1);
        return;
    }
    public static void A4(int stock[][],int K)
    {
        List<Integer>temp=new ArrayList<>();
        List<Integer>tempstock=new ArrayList<>();
        find(stock,K,1,temp,tempstock,0,-1);
        for(int i=0;i<list1.size();i=i+2)
        {
            System.out.println(""+ans1.get(i)+" "+list1.get(i)+" "+list1.get(i+1));
        }
        return;
    }

    public static void A5(int stock[][],int k)
    {
        int m=stock.length,n=stock[0].length;
        int dp[][][]=new int[k+1][m][n];
        for(int i=1;i<=k;i++)
        {
            for(int j=1;j<m;j++)
            {
                for(int p=2;p<n;p++)
                {
                    for(int q=1;q<p;q++)
                    {
                        dp[i][j][p]=Math.max(dp[i][j][p],stock[j][p]-stock[j][q]+dp[i-1][m-1][q]);    //
                    }
                    dp[i][j][p]=Math.max(dp[i][j][p],Math.max(dp[i][j-1][p],dp[i][j][p-1]));    //
                }
            }
        }
        System.out.println(""+dp[k][m-1][n-1]);
        List<Integer>list=new ArrayList<>();
        List<Integer>ans=new ArrayList<>();
        int index=n-1,t=k,s=m-1;
       // int p=dp[k][m-1][n-1];
        while(index>0 && t>0 && s>0)
        {
            if(dp[t][s][index]==dp[t][s][index-1])
                index--;
            else
            {
                if(dp[t][s][index]==dp[t][s-1][index])
                    s--;
                else
                {
                    for(int j=1;j<index;j++)
                    {
                        if(dp[t][s][index]==(dp[t-1][m-1][j]+stock[s][index]-stock[s][j]))
                        {
                            //System.out.println("j "+j+" index "+index+" s "+s);
                            list.add(j);
                            list.add(index);
                            ans.add(s);
                            ans.add(s);
                            index=j;
                            t--;
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

    public static void A6Bottom(int stock[][],int k)
    {
        int m=stock.length,n=stock[0].length;
        int dp[][][]=new int[k+1][m][n];
        for(int i=1;i<=k;i++)
        {
            for(int j=1;j<m;j++)
            {
                int prevdiff=-1000000000;
                for(int p=2;p<n;p++)
                {
                    prevdiff=Math.max(prevdiff,dp[i-1][m-1][p-1]-stock[j][p-1]);
                    dp[i][j][p]=Math.max(dp[i][j][p],stock[j][p]+prevdiff);
                    dp[i][j][p]=Math.max(dp[i][j][p],Math.max(dp[i][j-1][p],dp[i][j][p-1]));
                }
            }
        }
        System.out.println(""+dp[k][m-1][n-1]);
        List<Integer>list=new ArrayList<>();
        List<Integer>ans=new ArrayList<>();
        int index=n-1,t=k,s=m-1;
       // int p=dp[k][m-1][n-1];
        while(index>0 && t>0 && s>0)
        {
            if(dp[t][s][index]==dp[t][s][index-1])
                index--;
            else
            {
                if(dp[t][s][index]==dp[t][s-1][index])
                    s--;
                else
                {
                    for(int j=1;j<index;j++)
                    {
                        if(dp[t][s][index]==(dp[t-1][m-1][j]+stock[s][index]-stock[s][j]))
                        {
                            //System.out.println("j "+j+" index "+index+" s "+s);
                            list.add(j);
                            list.add(index);
                            ans.add(s);
                            ans.add(s);
                            index=j;
                            t--;
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


    public static int find(int stock[][],int k,int index,int dp[][])
    {
        int m=stock.length;
        if(k==0 || index<=0)
            return 0;
        if(dp[k][index]!=-1)
            return dp[k][index];
        int ans=-10000000;
        for(int j=index-1;j>=1;j--)
        {
            int max=-1000000000;
            for(int q=1;q<m;q++)
            {
                max=Math.max(max,stock[q][index]-stock[q][j]);
            }
            ans=Math.max(ans,max+find(stock,k-1,j,dp));
        }
        ans=Math.max(ans,find(stock,k,index-1,dp));
        return dp[k][index]=ans;
    }
    public static int find1(int stock[][],int k,int index,int dp[][],List<Integer>list,List<Integer>ans,int p)
    {
        int m=stock.length;
        if(k==0 || index<=0 || p==0)
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
        if(dp[k][index]!=p)
            return 0;
        for(int j=1;j<index;j++)
        {
            int max=-1000000000,maxindex=-1;
            for(int q=1;q<m;q++)
            {
                if(max<stock[q][index]-stock[q][j])
                {
                    max=stock[q][index]-stock[q][j];
                    maxindex=q;
                }
            }
                list.add(j);
                list.add(index);
                ans.add(maxindex);
                ans.add(maxindex);
                if(find1(stock,k-1,j,dp,list,ans,p-max)==1)
                    return 1;
                list.remove(list.size()-1);
                list.remove(list.size()-1);
                ans.remove(ans.size()-1);
                ans.remove(ans.size()-1);
        }
        if(find1(stock,k,index-1,dp,list,ans,p)==1)
            return 1;
        return 0;
    }
    public static void A6Memo(int stock[][],int k)
    {
        int n=stock[0].length;
        // int m=stock.length,
        int dp[][]=new int[k+1][n];
        for(int i=0;i<=k;i++)
        {
            for(int j=0;j<n;j++)
            {
                dp[i][j]=-1;
            }
        }
        int x=find(stock,k,stock[0].length-1,dp);
        System.out.println(""+x);
        List<Integer>list=new ArrayList<>();
        List<Integer>ans=new ArrayList<>();
        find1(stock,k,n-1,dp,list,ans,x);
        return;
    }
    
public static void main(String[] args) {
    Scanner sc=new Scanner(System.in);
    int K=sc.nextInt();
    int m=sc.nextInt();
    int n=sc.nextInt();
    int stock[][]=new int[m+1][n+1];
    //Random ran = new Random();
    for(int i=1;i<=m;i++)
    {
        for(int j=1;j<=n;j++)
        {
            stock[i][j]=(int) (Math.random()*(1000-1+1))+1;
            //stock[i][j]=random.nextInt(1000)+1;
            //stock[i][j]=sc.nextInt();
        }
    }
    sc.close();
    long startTime,endTime,totalTime;
    /*System.out.println("A1");
    long startTime = System.currentTimeMillis();
    A1(stock);
    long endTime   = System.currentTimeMillis();
    long totalTime = endTime - startTime;
    System.out.println(totalTime);
    
    System.out.println("");
    System.out.println("A2");

    startTime=System.currentTimeMillis();
    A2(stock);
    endTime   = System.currentTimeMillis();
    totalTime = endTime - startTime;
    System.out.println(totalTime);

    System.out.println("");
    System.out.println("A3Memo");

    startTime=System.currentTimeMillis();
    A3Memo(stock);
    endTime   = System.currentTimeMillis();
    totalTime = endTime - startTime;
    System.out.println(totalTime);

    System.out.println("");
    System.out.println("A3Bottom");

    startTime=System.currentTimeMillis();
    A3Bottom(stock);
    endTime   = System.currentTimeMillis();
    totalTime = endTime - startTime;
    System.out.println(totalTime);*/

    System.out.println("");
    System.out.println("A4");
    startTime=System.currentTimeMillis();
    A4(stock,K);
    endTime   = System.currentTimeMillis();
    totalTime = endTime - startTime;
    System.out.println(totalTime);

    System.out.println("");
    System.out.println("A5");
    startTime=System.currentTimeMillis();
    A5(stock,K);
    endTime   = System.currentTimeMillis();
    totalTime = endTime - startTime;
    System.out.println(totalTime);

    System.out.println("");
    System.out.println("A6Bottom");
    startTime=System.currentTimeMillis();
    A6Bottom(stock,K);
    endTime   = System.currentTimeMillis();
    totalTime = endTime - startTime;
    System.out.println(totalTime);

    System.out.println("");
    System.out.println("A6Memo");
    startTime=System.currentTimeMillis();
    A6Memo(stock,K);
    endTime   = System.currentTimeMillis();
    totalTime = endTime - startTime;
    System.out.println(totalTime);

 }
}
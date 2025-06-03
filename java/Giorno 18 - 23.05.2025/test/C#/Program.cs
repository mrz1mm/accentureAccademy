public class GenericTestCS
{
    public static void Main(string[] args)
    {
        // 1. INFORMAZIONI A COMPILE-TIME
        List<string> listString = new List<string>();
        listString.Add("Ciao");
        // listString.Add(123); // A compile-time, il compilatore sa che listString deve contenere solo stringhe

        List<int> listInteger = new List<int>();
        listInteger.Add(123);
        // listInteger.Add("Ciao"); // A compile-time, il compilatore sa che listInteger deve contenere solo interi


        // 2. INFORMAZIONI A RUNTIME
        Console.WriteLine("--- INFORMAZIONI A RUNTIME ---");

        try
        {
            Console.WriteLine("listString.GetType(): " + listString.GetType());
            Console.WriteLine("listInteger.GetType(): " + listInteger.GetType());

            if (listString.GetType() == listInteger.GetType())
            {
                Console.WriteLine("=> Sono istanze della stessa classe: " + 
                    listString.GetType().Name);
            }
            else
            {
                Console.WriteLine("=> Le classi sono DIVERSE a runtime: " + 
                    listString.GetType().Name + " e " + listInteger.GetType().Name);
            }
        }
        catch (Exception e)
        {
            Console.WriteLine("Errore: " + e.Message);
        }
    }
}
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace MvcApplication1.Models
{
    public class QueueModel
    {
    }


    public class Customer
    {
        public int customerID { get; set; }
        public string name { get; set; }
        public DateTime dob { get; set; }
        public string address { get; set; }
        public int zipcode { get; set; }
        public int mednum { get; set; }
        public string reason { get; set; }
        public string dobdateString {get;set;}
    }
}
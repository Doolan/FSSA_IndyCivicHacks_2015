//------------------------------------------------------------------------------
// <auto-generated>
//    This code was generated from a template.
//
//    Manual changes to this file may cause unexpected behavior in your application.
//    Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace MvcApplication1
{
    using System;
    using System.Collections.Generic;
    
    public partial class Person
    {
        public Person()
        {
            this.Vists = new HashSet<Vist>();
        }
    
        public int id { get; set; }
        public string first_name { get; set; }
        public string last_name { get; set; }
        public System.DateTime DOB { get; set; }
        public string address { get; set; }
        public Nullable<int> zipcode { get; set; }
        public string ssn { get; set; }
        public Nullable<int> mednum { get; set; }
    
        public virtual ICollection<Vist> Vists { get; set; }
    }
}

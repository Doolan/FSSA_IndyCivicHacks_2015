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
    
    public partial class Vist
    {
        public Vist()
        {
            this.ProgramsVieweds = new HashSet<ProgramsViewed>();
        }
    
        public int id { get; set; }
        public System.DateTime Timestamp { get; set; }
        public int LocationID { get; set; }
        public int PersonID { get; set; }
        public byte Reason { get; set; }
        public short CurrentQueue { get; set; }
    
        public virtual Office Office { get; set; }
        public virtual Person Person { get; set; }
        public virtual Queue Queue { get; set; }
        public virtual vistreason vistreason { get; set; }
        public virtual ICollection<ProgramsViewed> ProgramsVieweds { get; set; }
    }
}
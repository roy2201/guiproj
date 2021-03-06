USE [master]
GO
/****** Object:  Database [sparkrentdb]    Script Date: 3/11/2022 5:51:38 PM ******/
CREATE DATABASE [sparkrentdb]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'sparkrentdb', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\sparkrentdb.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'sparkrentdb_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\sparkrentdb_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [sparkrentdb] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [sparkrentdb].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [sparkrentdb] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [sparkrentdb] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [sparkrentdb] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [sparkrentdb] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [sparkrentdb] SET ARITHABORT OFF 
GO
ALTER DATABASE [sparkrentdb] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [sparkrentdb] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [sparkrentdb] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [sparkrentdb] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [sparkrentdb] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [sparkrentdb] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [sparkrentdb] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [sparkrentdb] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [sparkrentdb] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [sparkrentdb] SET  DISABLE_BROKER 
GO
ALTER DATABASE [sparkrentdb] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [sparkrentdb] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [sparkrentdb] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [sparkrentdb] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [sparkrentdb] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [sparkrentdb] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [sparkrentdb] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [sparkrentdb] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [sparkrentdb] SET  MULTI_USER 
GO
ALTER DATABASE [sparkrentdb] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [sparkrentdb] SET DB_CHAINING OFF 
GO
ALTER DATABASE [sparkrentdb] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [sparkrentdb] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [sparkrentdb] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [sparkrentdb] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [sparkrentdb] SET QUERY_STORE = OFF
GO
USE [sparkrentdb]
GO
/****** Object:  UserDefinedFunction [dbo].[fnViewReceipt]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION [dbo].[fnViewReceipt] (@Cid int, @NbDays int)
RETURNS INTEGER
AS
BEGIN
	DECLARE @total INTEGER;
	SET @total = (SELECT CPRICE FROM TBLCAR WHERE CRID = @Cid) * @NbDays
	RETURN @total
END
GO
/****** Object:  Table [dbo].[TBLADMINST]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TBLADMINST](
	[AD_ID] [int] IDENTITY(1,1) NOT NULL,
	[AD_P_ID] [varchar](30) NULL,
	[AD_PASS] [varchar](30) NULL,
	[AD_NAME] [nchar](10) NULL,
 CONSTRAINT [PK_TBLADMINST] PRIMARY KEY CLUSTERED 
(
	[AD_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  UserDefinedFunction [dbo].[fnAdminShowAdmin]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[fnAdminShowAdmin](@admin1_ID int)
RETURNS TABLE
RETURN (select AD_ID,AD_NAME,AD_PASS from TBLADMINST WHERE AD_P_ID = @admin1_ID)


-------------------------------------------------------------------

GO
/****** Object:  Table [dbo].[TBLSECRETARY]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TBLSECRETARY](
	[SEC_ID] [int] IDENTITY(1,1) NOT NULL,
	[SEC_AD_ID] [varchar](30) NULL,
	[SEC_NAME] [varchar](30) NULL,
	[SEC_PASS] [nchar](10) NULL,
	[SEC_P_ID] [nchar](10) NULL,
 CONSTRAINT [PK_TBLSECRETARY] PRIMARY KEY NONCLUSTERED 
(
	[SEC_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  UserDefinedFunction [dbo].[fnAdminShowSecretary]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/*
input: Admin1_ID int
output: table of all secretaries related to admin1
*/

CREATE FUNCTION [dbo].[fnAdminShowSecretary] (@admin1_ID int)
RETURNS TABLE
RETURN (select SEC_ID,SEC_NAME,SEC_PASS,SEC_AD_ID from tblsecretary WHERE SEC_AD_ID = @admin1_ID)

-----------------------------------------------------------------


/*
input: Admin1_ID int
output: table of all drivers related to admin1
*/
GO
/****** Object:  Table [dbo].[TBLDRIVER]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TBLDRIVER](
	[DRV_ID] [int] IDENTITY(1,1) NOT NULL,
	[DRV_AD_ID] [varchar](30) NULL,
	[DRV_SEC_ID] [varchar](30) NULL,
	[DRV_NAME] [nchar](10) NULL,
	[DRV_PASS] [nchar](10) NULL,
 CONSTRAINT [PK_TBLDRIVER] PRIMARY KEY NONCLUSTERED 
(
	[DRV_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  UserDefinedFunction [dbo].[fnAdminShowDriver]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[fnAdminShowDriver] (@admin1_ID int)
RETURNS TABLE
RETURN (select DRV_ID,DRV_NAME,DRV_PASS,DRV_AD_ID from TBLDRIVER WHERE DRV_AD_ID = @admin1_ID) 

----------------------------------------------------------------------

/*
input: Secretary1_ID int
output: table of all secretaries related to Secretary1
*/
GO
/****** Object:  UserDefinedFunction [dbo].[fnSecretaryShowSecretary]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[fnSecretaryShowSecretary] (@secretary1_ID int)
RETURNS TABLE
RETURN (select SEC_ID,SEC_NAME,SEC_PASS,SEC_P_ID from tblsecretary WHERE SEC_P_ID = @secretary1_ID)

---------------------------------------------------------------------------

GO
/****** Object:  UserDefinedFunction [dbo].[fnSecretaryShowDriver]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[fnSecretaryShowDriver] (@secretary1_ID int)
RETURNS TABLE
RETURN (select DRV_ID,DRV_NAME,DRV_PASS,DRV_SEC_ID from TBLDRIVER WHERE DRV_SEC_ID = @secretary1_ID) 
GO
/****** Object:  Table [dbo].[TBLCAR]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TBLCAR](
	[CRID] [int] IDENTITY(1,1) NOT NULL,
	[BID] [int] NOT NULL,
	[CCOLOR] [varchar](30) NULL,
	[CYEAR] [int] NULL,
	[CMODEL] [varchar](30) NULL,
	[CPRICE] [int] NULL,
	[ctype] [varchar](30) NULL,
 CONSTRAINT [PK_TBLCAR] PRIMARY KEY CLUSTERED 
(
	[CRID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TBLBRANCH]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TBLBRANCH](
	[BID] [int] IDENTITY(1,1) NOT NULL,
	[BNAME] [varchar](30) NULL,
 CONSTRAINT [PK_TBLBRANCH] PRIMARY KEY CLUSTERED 
(
	[BID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[vwCarsAndBranch]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[vwCarsAndBranch]
as
	select crid, ccolor, bname, cyear, cmodel, ctype, cprice from TBLCAR
	inner join TBLBRANCH on TBLBRANCH.BID = TBLCAR.BID
GO
/****** Object:  Table [dbo].[TBLCOMMENT]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TBLCOMMENT](
	[CID] [int] NOT NULL,
	[CTID] [int] IDENTITY(1,1) NOT NULL,
	[CTVALUE] [varchar](30) NULL,
 CONSTRAINT [PK_TBLCOMMENT] PRIMARY KEY CLUSTERED 
(
	[CTID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TBLCUSTOMER]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TBLCUSTOMER](
	[CID] [int] IDENTITY(1,1) NOT NULL,
	[CUNAME] [varchar](30) NULL,
	[CPASS] [varchar](30) NULL,
	[ispremium] [bit] NULL,
 CONSTRAINT [PK_TBLCUSTOMER] PRIMARY KEY CLUSTERED 
(
	[CID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[vwUnameWithComment]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

create view [dbo].[vwUnameWithComment]
as
	select cuname , ctvalue
	from TBLCUSTOMER as c, TBLCOMMENT as ct
	where c.cid = ct.CID
GO
/****** Object:  Table [dbo].[TBLACCOUNT]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TBLACCOUNT](
	[ACID] [int] NOT NULL,
	[CID] [int] NOT NULL,
 CONSTRAINT [PK_TBLACCOUNT] PRIMARY KEY CLUSTERED 
(
	[ACID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TBLCREDITCARD]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TBLCREDITCARD](
	[ACID] [int] IDENTITY(1,5) NOT NULL,
	[CARDNUMBER] [int] NOT NULL,
	[CID] [int] NULL,
	[HOLDERNAME] [varchar](30) NULL,
	[EXPIRYDATE] [datetime] NULL,
	[CVV] [int] NULL,
	[BALANCE] [int] NULL,
 CONSTRAINT [PK_TBLCREDITCARD] PRIMARY KEY CLUSTERED 
(
	[ACID] ASC,
	[CARDNUMBER] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TBLEMPLOYEE]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TBLEMPLOYEE](
	[EID] [int] IDENTITY(1,1) NOT NULL,
	[EUNAME] [varchar](30) NULL,
	[EPASS] [varchar](30) NULL,
 CONSTRAINT [PK_TBLEMPLOYEE] PRIMARY KEY CLUSTERED 
(
	[EID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TBLINSURANCE]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TBLINSURANCE](
	[IID] [int] IDENTITY(1,1) NOT NULL,
	[CRID] [int] NOT NULL,
	[ISTATUS] [char](10) NULL,
 CONSTRAINT [PK_TBLINSURANCE] PRIMARY KEY CLUSTERED 
(
	[IID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TBLLOGGED]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TBLLOGGED](
	[LID] [int] NULL,
	[CST] [bit] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TBLPAYPAL]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TBLPAYPAL](
	[ACID] [int] IDENTITY(1,1) NOT NULL,
	[EMAIL] [varchar](30) NOT NULL,
	[CID] [int] NULL,
	[PPASS] [varchar](30) NULL,
	[BALANCE] [int] NULL,
 CONSTRAINT [PK_TBLPAYPAL] PRIMARY KEY CLUSTERED 
(
	[ACID] ASC,
	[EMAIL] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TBLRENT]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TBLRENT](
	[CID] [int] NOT NULL,
	[CRID] [int] NOT NULL,
 CONSTRAINT [PK_TBLRENT] PRIMARY KEY CLUSTERED 
(
	[CID] ASC,
	[CRID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TBLTRANSACTION]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TBLTRANSACTION](
	[TID] [int] IDENTITY(1,1) NOT NULL,
	[CRID] [int] NOT NULL,
	[TAMOUNT] [int] NULL,
	[TDAYS] [int] NULL,
	[RTDAY] [datetime] NULL,
	[CID] [int] NULL,
 CONSTRAINT [PK_TBLTRANSACTION] PRIMARY KEY CLUSTERED 
(
	[TID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Index [ASSOCIATION_4_FK]    Script Date: 3/11/2022 5:51:38 PM ******/
CREATE NONCLUSTERED INDEX [ASSOCIATION_4_FK] ON [dbo].[TBLCAR]
(
	[BID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [ASSOCIATION_5_FK]    Script Date: 3/11/2022 5:51:38 PM ******/
CREATE NONCLUSTERED INDEX [ASSOCIATION_5_FK] ON [dbo].[TBLCOMMENT]
(
	[CID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [ASSOCIATION_3_FK]    Script Date: 3/11/2022 5:51:38 PM ******/
CREATE NONCLUSTERED INDEX [ASSOCIATION_3_FK] ON [dbo].[TBLINSURANCE]
(
	[CRID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [INHERITANCE_2_FK]    Script Date: 3/11/2022 5:51:38 PM ******/
CREATE NONCLUSTERED INDEX [INHERITANCE_2_FK] ON [dbo].[TBLPAYPAL]
(
	[ACID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [TBLRENT_FK]    Script Date: 3/11/2022 5:51:38 PM ******/
CREATE NONCLUSTERED INDEX [TBLRENT_FK] ON [dbo].[TBLRENT]
(
	[CID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [TBLRENT2_FK]    Script Date: 3/11/2022 5:51:38 PM ******/
CREATE NONCLUSTERED INDEX [TBLRENT2_FK] ON [dbo].[TBLRENT]
(
	[CRID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [ASSOCIATION_2_FK]    Script Date: 3/11/2022 5:51:38 PM ******/
CREATE NONCLUSTERED INDEX [ASSOCIATION_2_FK] ON [dbo].[TBLTRANSACTION]
(
	[CRID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  StoredProcedure [dbo].[isPremium]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

create proc [dbo].[isPremium]
	@cid	int,
	@premium	bit	output
as
	set @premium = (select ispremium from TBLCUSTOMER where CID = @cid)
GO
/****** Object:  StoredProcedure [dbo].[isValidCreditCard]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO



CREATE proc [dbo].[isValidCreditCard]
	@holdername	varchar(30),
	@cvv	int,
	--@expiryDate date,
	@cardnumber	int,
	@errorcode	int	OUTPUT,
	@cid	int
	/*usually a valid credit card function does not take a cid input*/
	/*in our implementation we used this to show that a known customer
	can ONLY pay through credit cards that he added to his profile*/
as
	set @errorcode = 2
	if(exists (select * from TBLCREDITCARD as c, TBLCUSTOMER as ct
				where c.CID = @cid and c.CVV = @cvv and c.CARDNUMBER = @cardnumber
				and c.HOLDERNAME = @holdername ))
	begin
		print 'yes credit exists'
		set @errorcode = 1
	end
GO
/****** Object:  StoredProcedure [dbo].[isValidPaypal]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


CREATE proc [dbo].[isValidPaypal]
	@email	varchar(30),
	@pass	varchar(30),
	@errorcode	int	OUTPUT,
	@cid	int
as
	set @errorcode = 2
	if(exists (select * from TBLPAYPAL as p, TBLCUSTOMER as c
				where p.CID = c.CID and p.EMAIL = @email and p.PPASS = @pass) )
	begin
		set @errorcode = 1
	end
GO
/****** Object:  StoredProcedure [dbo].[spAddCar]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[spAddCar]
    @branch_id int,
    @color VARCHAR(30),
    @year VARCHAR(30),
    @model VARCHAR(30),
    @price VARCHAR(30),
    @type VARCHAR(30)

AS
    INSERT into tblcar (BID,CCOLOR,CYEAR,CMODEL,CPRICE,CTYPE)
    VALUES ( @branch_id , @color , @year , @model ,@price ,@type)

--------------------------------------------------------------------------
GO
/****** Object:  StoredProcedure [dbo].[spAddTran]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE proc [dbo].[spAddTran]
	@cid	int,
	@crid	int,
	@nbdays	int,
	@amount	int
as
	insert into TBLTRANSACTION (CRID, TAMOUNT, TDAYS, RTDAY, CID)
	values
	(@crid, @amount, @nbdays, getdate(), @cid)
GO
/****** Object:  StoredProcedure [dbo].[spAdminAddAdmin]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


CREATE PROC [dbo].[spAdminAddAdmin]
    @admin1_ID int,
    @admin2_Name VARCHAR(30),
    @admin2_Pass VARCHAR(30),
    @errorcode int OUTPUT
AS
    if exists (SELECT AD_ID FROM TBLADMINST WHERE AD_ID = @admin1_ID)
        BEGIN
        SET @errorcode = 1;
        PRINT 'The new admin is added successfuly'

        INSERT into TBLADMINST (AD_NAME,AD_PASS,AD_P_ID)
        VALUES (@admin2_Name,@admin2_Pass,@admin1_ID)

        END

    ELSE
        BEGIN

        set @errorcode = -1;
        PRINT 'No admin with such ID'

        END 
----------------------------------------------------------------

/*
input: Admin1_ID int, Admin2_ID int
output: errorcode int
*/
GO
/****** Object:  StoredProcedure [dbo].[spAdminAddDriver]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

--------------------------------------------------------------------------------------------


/*
input: Admin1_ID int, driver_Name varchar, driver_Pass varchar
output: errorcode int 
*/

CREATE PROC [dbo].[spAdminAddDriver]
    @admin1_ID int,
    @driver_Name VARCHAR(30),
    @driver_Pass VARCHAR(30),
    @errorcode int OUTPUT
AS
    if exists (SELECT AD_ID FROM TBLADMINST WHERE AD_ID = @admin1_ID)
        BEGIN
        SET @errorcode = 1;
        PRINT 'The new driver is added successfuly'

        INSERT into TBLDRIVER (DRV_AD_ID,DRV_NAME,DRV_PASS,DRV_SEC_ID)
        VALUES (@admin1_ID,@driver_Name,@driver_Pass,-1)

        END

    ELSE
        BEGIN

        set @errorcode = -1;
        PRINT 'No admin with such ID'

        END 

-------------------------------------------------------------------------------

/*
input: Admin1_ID int, SEC_ID int
output: errorcode int
*/

GO
/****** Object:  StoredProcedure [dbo].[spAdminAddSecretary]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
------------------------------------------------------------------------------

/*
input: Admin1_ID int, Admin2_Name varchar, Admin2_Pass varchar
output: errorcode int 
*/

CREATE PROC [dbo].[spAdminAddSecretary]
    @admin1_ID int,
    @sec_Name VARCHAR(30),
    @sec_Pass VARCHAR(30),
    @errorcode int OUTPUT
AS
    if exists (SELECT AD_ID FROM TBLADMINST WHERE AD_ID = @admin1_ID)
        BEGIN
        SET @errorcode = 1;
        PRINT 'The new secretary is added successfuly'

        INSERT into TBLSECRETARY (SEC_AD_ID,sec_Name,sec_Pass,SEC_P_ID)
        VALUES (@admin1_ID,@sec_Name,@sec_Pass,-1)

        END

    ELSE
        BEGIN

        set @errorcode = -1;
        PRINT 'No admin with such ID'

        END


------------------------------------------------------------------

GO
/****** Object:  StoredProcedure [dbo].[spAdminLogin]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


---------------------------------------------------------------------------

CREATE PROC [dbo].[spAdminLogin]
    @admin_name VARCHAR(30),
    @admin_pass VARCHAR(30),
    @admin_ID int OUTPUT,
    @errorcode int OUTPUT

AS
    IF exists (SELECT AD_ID from TBLADMINST WHERE AD_NAME = @admin_name AND AD_PASS = @admin_pass)
        BEGIN
            set @admin_ID = (SELECT AD_ID from TBLADMINST WHERE AD_NAME = @admin_name AND AD_PASS = @admin_pass);
            set @errorcode = 1;


        END


    ELSE
        BEGIN
            set @errorcode = -1;
            PRINT 'Enter valid admin information'

        END

--------------------------------------------------------------

GO
/****** Object:  StoredProcedure [dbo].[spAdminRemoveAdmin]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[spAdminRemoveAdmin]
    @admin1_ID int,
    @admin2_ID int,
    @errorcode int OUTPUT

AS
    if exists (select AD_ID from TBLADMINST WHERE AD_ID = @admin1_ID)
        BEGIN
            if exists (select AD_ID FROM TBLADMINST WHERE AD_ID = @admin2_ID)
                BEGIN

                    DECLARE @parentID int
                    set @parentID = (select AD_P_ID FROM TBLADMINST WHERE AD_ID = @admin2_ID)
                    if @parentID = @admin1_ID
                        BEGIN
                            SET @errorcode=1;
                            PRINT 'The admin is removed successfully'

                            DELETE from TBLADMINST
                            WHERE AD_ID = @admin2_ID
                        
                        END
                    
                    ELSE
                        BEGIN

                            SET @errorcode = -2
                            PRINT 'Cant remove admin, this admin is refered to anaother manager'

                        END

                    

                END
            
            else
                BEGIN
                    SET @errorcode = 0;
                    PRINT 'No admin with such ID to be removed'
                END

        END

    ELSE
        BEGIN

            set @errorcode = -1;
            PRINT 'No admin with such ID'

        END
GO
/****** Object:  StoredProcedure [dbo].[spAdminRemoveDriver]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROC [dbo].[spAdminRemoveDriver]
    @admin1_ID int,
    @driver_ID int,
    @errorcode int OUTPUT

AS
    if exists (select AD_ID from TBLADMINST WHERE AD_ID = @admin1_ID)
        BEGIN
            if exists (select DRV_ID FROM TBLDRIVER WHERE DRV_ID = @driver_ID)
                BEGIN

                    DECLARE @parentID int
                    set @parentID = (select DRV_AD_ID FROM TBLDRIVER WHERE DRV_ID = @driver_ID)
                    if @parentID = @admin1_ID
                        BEGIN
                            SET @errorcode=1;
                            PRINT 'The driver is removed successfully'

                            DELETE from TBLDRIVER
                            WHERE DRV_ID = @driver_ID
                        
                        END
                    
                    ELSE
                        BEGIN

                            SET @errorcode = -2
                            PRINT 'Cant remove driver, this driver is refered to anaother manager'

                        END

                    

                END
            
            else
                BEGIN
                    SET @errorcode = 0;
                    PRINT 'No secretary with such ID to be removed'
                END

        END

    ELSE
        BEGIN

            set @errorcode = -1;
            PRINT 'No admin with such ID'

        END
GO
/****** Object:  StoredProcedure [dbo].[spAdminRemoveSecretary]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/*
input: Admin1_ID int, SEC_ID int
output: errorcode int
*/


CREATE PROC [dbo].[spAdminRemoveSecretary]
    @admin1_ID int,
    @secretary_ID int,
    @errorcode int OUTPUT

AS
    if exists (select AD_ID from TBLADMINST WHERE AD_ID = @admin1_ID)
        BEGIN
            if exists (select SEC_ID FROM TBLSECRETARY WHERE SEC_ID = @secretary_ID)
                BEGIN

                    DECLARE @parentID int
                    set @parentID = (select SEC_AD_ID FROM TBLSECRETARY WHERE SEC_ID = @secretary_ID)
                    if @parentID = @admin1_ID
                        BEGIN
                            SET @errorcode=1;
                            PRINT 'The secreatry is removed successfully'

                            DELETE from TBLSECRETARY
                            WHERE SEC_ID = @secretary_ID
                        
                        END
                    
                    ELSE
                        BEGIN

                            SET @errorcode = -2
                            PRINT 'Cant remove secretary, this secretary is refered to anaother manager'

                        END

                    

                END
            
            else
                BEGIN
                    SET @errorcode = 0;
                    PRINT 'No secretary with such ID to be removed'
                END

        END

    ELSE
        BEGIN

            set @errorcode = -1;
            PRINT 'No admin with such ID'

        END
GO
/****** Object:  StoredProcedure [dbo].[spComment]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create proc [dbo].[spComment]
	@cid	int,
	@ctvalue	varchar(30)
as
	insert into TBLCOMMENT (cid, ctvalue)
	values
	(@cid, @ctvalue)
GO
/****** Object:  StoredProcedure [dbo].[spGetCards]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create proc [dbo].[spGetCards]
	@cid	int
as
	select CARDNUMBER, HOLDERNAME, EXPIRYDATE, CVV, BALANCE
	from TBLCREDITCARD
	where CID = @cid
GO
/****** Object:  StoredProcedure [dbo].[spGetLoggedUsername]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


CREATE proc [dbo].[spGetLoggedUsername]
	@cid	int,
	@uname	varchar(30) output
as
	set @uname = (
	select distinct CUNAME
	from TBLCUSTOMER
	where CID =@cid
	)
	print @uname
GO
/****** Object:  StoredProcedure [dbo].[spGetPaypal]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

create proc [dbo].[spGetPaypal]
	@cid	int
as
	select email, ppass, balance 
	from TBLPAYPAL 
	where CID = @cid
GO
/****** Object:  StoredProcedure [dbo].[spLogIn]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROC [dbo].[spLogIn]
	@uname	varchar(30),
	@pass	varchar(30),
	@cid		int	OUTPUT,
	@errorcode	int	OUTPUT
AS
	-- if login is invalid
	-- if customer is offline
	-- if customer doesn't exists in table logged (first login)
	set @errorcode = 1
	if (exists(select * from TBLCUSTOMER where CUNAME = @uname and CPASS = @pass))
	begin
		print 'valid credentails'
		set @cid = (select cid from TBLCUSTOMER where cuname = @uname)
		if (exists(select * from TBLLOGGED where lid = @cid))
		begin
			update TBLLOGGED
			set cst = 1 where lid = @cid
		end
		else
		begin
			insert into TBLLOGGED values (@cid, 1)
		end
	end
	else
	begin
		set @cid = -1
		set @errorcode = 2
		print 'invalid credentails'
		return ;
	end
GO
/****** Object:  StoredProcedure [dbo].[spLogOut]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create proc [dbo].[spLogOut]
	@cid	int
as
	update TBLLOGGED
	set cst = 0 where lid = @cid
GO
/****** Object:  StoredProcedure [dbo].[spPayCredit]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create proc [dbo].[spPayCredit]
	@amount	int,
	@cardnumber	int,
	@errorcode	int	OUTPUT
as
	begin
		declare @currentBalance int;
		set @errorcode = 1
		set @currentBalance = (select balance from TBLCREDITCARD as c where c.CARDNUMBER = @cardnumber)
		if(@currentBalance - @amount < 0)
		begin
			print ' not enough balance '
			set @errorcode = 2
			return ;
		end
		else
		begin
			begin transaction
				update TBLCREDITCARD
				set BALANCE = BALANCE - @amount
				where CARDNUMBER = @cardnumber
				print 'done tran ++++++++'
			commit transaction
		end
	end
GO
/****** Object:  StoredProcedure [dbo].[spPaypal]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE proc [dbo].[spPaypal]
	@amount	int,
	@email	varchar(30),
	@errorcode	int	OUTPUT
as
	begin
		declare @currentBalance int;
		set @errorcode = 1
		set @currentBalance = (select balance from TBLPAYPAL as p where p.EMAIL = @email)
		if(@currentBalance - @amount < 0)
		begin
			print ' not enough balance '
			set @errorcode = 2
			return ;
		end
		else
		begin
			begin transaction
				update TBLPAYPAL
				set BALANCE = BALANCE - @amount
				where EMAIL = @email
				print 'done tran ++++++++'
			commit transaction
		end
	end
GO
/****** Object:  StoredProcedure [dbo].[spRemoveCar]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[spRemoveCar]
    @car_id int,
    @errorcode int OUTPUT
AS
    if EXISTS (SELECT * from TBLCAR WHERE CRID = @car_id)
        BEGIN
            DELETE from TBLCAR WHERE CRID = @car_id
            set @errorcode = 1;
            PRINT 'The car is removed successfully'

        END

    ELSE
        BEGIN
            SET @errorcode = 0;
            PRINT 'No car with such ID to be removed'
        end
GO
/****** Object:  StoredProcedure [dbo].[spSecretaryAddDriver]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


--------------------------------------------------------------------------

/*
input: Secretary1_ID int, driver_Name varchar, driver_Pass varchar
output: errorcode int 
*/

CREATE PROC [dbo].[spSecretaryAddDriver]
    @secretary1_ID int,
    @driver_Name VARCHAR(30),
    @driver_Pass VARCHAR(30),
    @errorcode int OUTPUT
AS
    if exists (SELECT SEC_ID FROM TBLSECRETARY WHERE SEC_ID = @secretary1_ID)
        BEGIN
        SET @errorcode = 1;
        PRINT 'The new driver is added successfuly'

        INSERT into TBLDRIVER (DRV_SEC_ID,DRV_NAME,DRV_PASS,DRV_AD_ID)
        VALUES (@secretary1_ID,@driver_Name,@driver_Pass,-1)

        END

    ELSE
        BEGIN

        set @errorcode = -1;
        PRINT 'No secretary with such ID'

        END 


----------------------------------------------------------------------------
GO
/****** Object:  StoredProcedure [dbo].[spSecretaryAddSecretary]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


----------------------------------------------------------------------------

/*
input: Secretary1_ID int, Secretary2_Name varchar, Secretary2_Pass varchar
output: errorcode int 
*/

CREATE PROC [dbo].[spSecretaryAddSecretary]
    @secretary1_ID int,
    @secretary2_Name VARCHAR(30),
    @secretary2_Pass VARCHAR(30),
    @errorcode int OUTPUT
AS
    if exists (SELECT SEC_ID FROM TBLSECRETARY WHERE SEC_ID = @secretary1_ID)
        BEGIN
        SET @errorcode = 1;
        PRINT 'The new secretary is added successfuly'

        INSERT into TBLSECRETARY (SEC_P_ID,SEC_NAME,SEC_PASS,SEC_AD_ID)
        VALUES (@secretary1_ID,@secretary2_Name,@secretary2_Pass,-1)

        END

    ELSE
        BEGIN

        set @errorcode = -1;
        PRINT 'No secretary with such ID'

        END 


DECLARE @id int, @name VARCHAR(30), @pass VARCHAR(30), @err int 
set @id = 1;
set @name = 'temp'; 
SET @pass= 'temp123';

EXEC dbo.spSecretaryAddSecretary @id ,@name,@pass,@err out


--------------------------------------------------------------------------------
    


/*
input: Secretary1_ID int, Secretary2_ID int
output: errorcode int
*/



GO
/****** Object:  StoredProcedure [dbo].[spSecretaryLogin]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROC [dbo].[spSecretaryLogin]
    @secretary_name VARCHAR(30),
    @secretary_pass VARCHAR(30),
    @secretary_ID int OUTPUT,
    @errorcode int OUTPUT

AS
    IF exists (SELECT SEC_ID from TBLSECRETARY WHERE SEC_NAME = @secretary_name AND SEC_PASS = @secretary_pass)
        BEGIN
            set @secretary_ID = (SELECT SEC_ID from TBLSECRETARY WHERE SEC_NAME = @secretary_name AND SEC_PASS = @secretary_pass);
            set @errorcode = 1;


        END


    ELSE
        BEGIN
            set @errorcode = -1;
            PRINT 'Enter valid secretary information'

        END


-------------------------------------------------------------------

SELECT * FROM TBLCAR
GO
/****** Object:  StoredProcedure [dbo].[spSecretaryRemoveDriver]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

/*
input: Secretary1_ID int, Driver_ID int
output: errorcode int
*/

CREATE PROC [dbo].[spSecretaryRemoveDriver]
    @secretary1_ID int,
    @driver_ID int,
    @errorcode int OUTPUT

AS
    if exists (select SEC_ID from TBLSECRETARY WHERE SEC_ID = @secretary1_ID)
        BEGIN
            if exists (select DRV_ID FROM TBLDRIVER WHERE DRV_ID = @driver_ID)
                BEGIN

                    DECLARE @parentID int
                    set @parentID = (select DRV_SEC_ID FROM TBLDRIVER WHERE DRV_ID = @driver_ID)
                    if @parentID = @secretary1_ID
                        BEGIN
                            SET @errorcode=1;
                            PRINT 'The driver is removed successfully'

                            DELETE from TBLDRIVER
                            WHERE DRV_ID = @driver_ID
                        
                        END
                    
                    ELSE
                        BEGIN

                            SET @errorcode = -2
                            PRINT 'Cant remove driver, this driver is refered to anaother manager'

                        END

                    

                END
            
            else
                BEGIN
                    SET @errorcode = 0;
                    PRINT 'No driver with such ID to be removed'
                END

        END

    ELSE
        BEGIN

            set @errorcode = -1;
            PRINT 'No secretary with such ID'

        END
GO
/****** Object:  StoredProcedure [dbo].[spSecretaryRemoveSecretary]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[spSecretaryRemoveSecretary]
    @secretary1_ID int,
    @secretary2_ID int,
    @errorcode int OUTPUT


AS
    if exists (select SEC_ID from TBLSECRETARY WHERE SEC_ID = @secretary1_ID)
        BEGIN
            if exists (select SEC_ID FROM TBLSECRETARY WHERE SEC_ID = @secretary2_ID)
                BEGIN

                    DECLARE @parentID int
                    set @parentID = (select SEC_P_ID FROM TBLSECRETARY WHERE SEC_ID = @secretary2_ID)
                    if @parentID = @secretary1_ID
                        BEGIN
                            SET @errorcode=1;
                            PRINT 'The secreatry is removed successfully'

                            DELETE from TBLSECRETARY
                            WHERE SEC_ID = @secretary2_ID
                        
                        END
                    
                    ELSE
                        BEGIN

                            SET @errorcode = -2
                            PRINT 'Cant remove secretary, this secretary is refered to anaother manager'

                        END

                    

                END
            
            else
                BEGIN
                    SET @errorcode = 0;
                    PRINT 'No secretary with such ID to be removed'
                END

        END

    ELSE
        BEGIN

            set @errorcode = -1;
            PRINT 'No secretary with such ID'

        END
GO
/****** Object:  StoredProcedure [dbo].[spShowCards]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


CREATE proc [dbo].[spShowCards]
	@cid	int
as
	select CARDNUMBER as [Card number], HOLDERNAME as [Holder name]
	, Convert(date, EXPIRYDATE) as [exp date], CVV as cvv, BALANCE as Balance
	from TBLCREDITCARD
	where CID = @cid
GO
/****** Object:  StoredProcedure [dbo].[spShowTran]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO



CREATE proc [dbo].[spShowTran]
	@cid	int
as
	select CRID as [card Id], TAMOUNT as amount, TDAYS  as [nb days],
	Convert(date, RTDAY) as [rented day]
	from TBLTRANSACTION
	where CID = @cid
GO
/****** Object:  StoredProcedure [dbo].[spSignUp]    Script Date: 3/11/2022 5:51:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


CREATE PROC [dbo].[spSignUp]
	@uname	varchar(30),
	@pass	varchar(30),
	@errcode	int	OUTPUT
AS
	set @errcode = 1
	if (exists (SELECT * FROM TBLCUSTOMER WHERE CUNAME = @uname))
	begin
		set @errcode = 270
	end
	else
	begin
		INSERT INTO  TBLCUSTOMER(CUNAME,CPASS,ispremium)
		VALUES
		(lower(@uname), lower(@pass), 0)
	end
GO
USE [master]
GO
ALTER DATABASE [sparkrentdb] SET  READ_WRITE 
GO

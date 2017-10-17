USE [SavantPlanning]
GO

/****** Object:  StoredProcedure [FORM].[FamilyTree]    Script Date: 03/03/2014 09:54:41 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


	
--declare
CREATE PROCEDURE [FORM].[FamilyTree] 
	@PersonID AS UNIQUEIDENTIFIER='12B3765A-D84C-4259-B6E5-368B660ABEDB'
AS

		SET NOCOUNT ON
		DECLARE @FamilyID AS UNIQUEIDENTIFIER
			SELECT @FamilyID = FamilyID FROM FORM.vFamilyMembers WHERE PersonID=@PersonID	
		 
		--DROP TABLE #Family	
	
	-- SET FAMILY TREE COLORS
		DECLARE @Colors TABLE (ColorID int IDENTITY(0,1), Color varchar(8))
			INSERT @Colors (Color)
			VALUES('#244062'), ('#808080'), ('#4F6228')
				, ('#403151'), ('#974706'), ('#632523')
				
			
				
		CREATE TABLE #Family (RowID INT IDENTITY(1,1), eID int, PersonID UNIQUEIDENTIFIER, ContactID UNIQUEIDENTIFIER, Person VARCHAR(100)
			,LastName VARCHAR(50), FirstName varchar(50), FamilyTree INT, FamilyBranch INT, FamilyColor VARCHAR(8), FamilyOrder INT, SubFamily int
			, ParentsID int, ParentID1 INT, Parent1 VARCHAR(100), ParentID2 INT, Parent2 VARCHAR(100), Children int
			, SpouseID INT, Spouse VARCHAR(100), SpousesID int, SpouseColor VARCHAR(8)
			, Gender varchar(1), Age INT, BirthDate DATE, DateOfDeath Date, Generation int, HOrder int,Comments VARCHAR(1000)
		 CONSTRAINT [PK_SectionsTmp] PRIMARY KEY CLUSTERED 
				(Generation ASC, FamilyBranch, RowID ) ON [PRIMARY])
	
		INSERT #Family (PersonID, eID, Person, ContactID, Gender, Generation, HOrder, Age, BirthDate, DateOfDeath, Comments
			, LastName, FirstName, FamilyBranch, FamilyTree)
			SELECT PersonID, eID, FullName, ContactID, Gender, 0 AS Generation, 0 AS HOrder, Age, BirthDate, DateOfDeath, Comments
				, LastName, FirstName, 0 AS FamilyBranch, 1 AS FamilyTree
			FROM FORM.vFamilyMembers
			WHERE FamilyID=@FamilyID
		
			
		UPDATE f SET SpouseID=fp.RowID, Spouse=fp.Person
			FROM #Family f 
				JOIN FORM.FamilyRelationships r ON f.PersonID=r.PersonID
				JOIN #Family fp ON r.RelatedPersonID=fp.PersonID
			WHERE RelationshipType='Spouse'
		
			
		
	-- CHILD PARENTS: Build a list of each child's parents
		DECLARE @ChildParents TABLE (RowID INT, Person VARCHAR(100), Parent1ID INT, Parent1 varchar(100), Parent2ID INT, Parent2 VARchar(100), ParentsID int)
			INSERT @ChildParents(RowID, Person, Parent1ID, Parent2ID)
		
				SELECT p1.RowID, p1.Person, MIN(p1.ParentID) AS ParentID1, MAX(p2.ParentID) AS ParentID2 FROM 
					(SELECT f.RowID, f.Person,  p.RowID AS ParentID, p.Person AS Parent
					FROM FORM.FamilyRelationships r
						JOIN #Family f ON r.PersonID=f.PersonID
						JOIN #Family p ON r.RelatedPersonID=p.PersonID
					WHERE r.RelationshipType='Parent'
					
					) p1
					
					LEFT JOIN 
					
					(SELECT f.RowID, f.Person,  p.RowID AS ParentID, p.Person AS Parent
					FROM FORM.FamilyRelationships r
						JOIN #Family f ON r.PersonID=f.PersonID
						JOIN #Family p ON r.RelatedPersonID=p.PersonID
					WHERE r.RelationshipType='Parent'
					
					) p2 ON p1.RowID=p2.RowID
					
					GROUP BY p1.RowID, p1.Person
						ORDER BY p1.RowID
				
					UPDATE @ChildParents SET Parent2ID=NULL
						WHERE Parent1ID=Parent2ID
						
					UPDATE p SET Parent1=f.Person
						FROM @ChildParents p JOIN #Family f ON p.Parent1ID=f.RowID
					
					UPDATE p SET Parent2=f.Person
						FROM @ChildParents p JOIN #Family f ON p.Parent2ID=f.RowID
					
					
		-- PARENTS: Build a list of the unique parent relationships based upon the children
			DECLARE @Parents TABLE (ParentsID INT IDENTITY(100,1), Parent1ID INT, Parent1 varchar(100), Parent2ID INT, Parent2 VARchar(100)
				, Anniversary DATE, DivorceDate Date)
				INSERT @Parents(Parent1ID, Parent1, Parent2ID, Parent2)
				SELECT Parent1ID, Parent1, Parent2ID, Parent2 
					FROM @ChildParents
					GROUP BY Parent1ID, Parent1, Parent2ID, Parent2
					
			
			UPDATE cp SET ParentsID=p.ParentsID
				FROM @ChildParents cp 
					JOIN @Parents p ON ISNULL(cp.Parent1,'') =  ISNULL(p.Parent1,'') AND ISNULL(cp.Parent2,'') =  ISNULL(p.Parent2,'')
					
					
	
		-- NOW PASS THE PARENT INFORMATION TO THE #Family TABLE
			UPDATE f SET ParentID1=cp.Parent1ID, Parent1=cp.Parent1, ParentID2=cp.Parent2ID, Parent2=cp.Parent2, ParentsID=cp.ParentsID
				FROM @ChildParents cp 
					JOIN #Family f ON cp.RowID=f.RowID
			
			
				
			
				
		-- DETERMINE THE FIRST GENERATION
			DECLARE @Generation AS INT=100
						
			UPDATE F SET Generation=@Generation, FamilyBranch=@Generation
				FROM #Family  F
				WHERE ParentsID IS NULL
					AND RowID NOT IN (SELECT SpouseID FROM #Family WHERE ParentsID IS NOT NULL AND SpouseID IS NOT null)
			
				WHILE (SELECT COUNT(*) FROM #Family WHERE Generation=0)>0
					BEGIN
			
					-- SET SPOUSE GENERATION
					UPDATE c SET Generation=@Generation+100 FROM #Family c JOIN #Family s ON c.SpouseID=s.RowID
						WHERE s.ParentsID IN 
						(SELECT ParentsID FROM @Parents cp
							JOIN (SELECT RowID FROM #Family WHERE Generation=@Generation) p ON cp.Parent1ID=p.RowID
							LEFT JOIN (SELECT RowID FROM #Family WHERE Generation=@Generation) m ON cp.Parent2ID=m.RowID)
					
					-- SET CHILD GENERATION		
					UPDATE F SET Generation=@Generation+100 FROM #Family f WHERE ParentsID IN 
						(SELECT ParentsID FROM @Parents cp
							JOIN (SELECT RowID FROM #Family WHERE Generation=@Generation) p ON cp.Parent1ID=p.RowID
							LEFT JOIN (SELECT RowID FROM #Family WHERE Generation=@Generation) m ON cp.Parent2ID=m.RowID)
					
						SET @Generation=@Generation + 100		   
							   
					END		
				
			
				
				UPDATE f SET SpousesID=p.ParentsID
					FROM @Parents p JOIN #Family f ON p.Parent1ID=f.RowID
				
				UPDATE f SET SpousesID=p.ParentsID
					FROM @Parents p JOIN #Family f ON p.Parent2ID=f.RowID
					
					
					
			DECLARE @Trees TABLE (FamilyTree int IDENTITY(1,1), SpousesID int)
				INSERT @Trees(SpousesID)
					SELECT SpousesID
					FROM #Family F 
						LEFT JOIN (SELECT ParentsID FROM #Family WHERE PersonID=@PersonID) cp ON f.SpousesID=cp.ParentsID
						WHERE Generation=100
					GROUP BY SpousesID
					
			
			UPDATE f SET FamilyTree=t.FamilyTree
				FROM #Family f JOIN @Trees t ON f.SpousesID=t.SpousesID	
				
						
				UPDATE f SET SpouseColor=c.Color
					FROM #Family f 
						JOIN @Colors c ON f.SpousesID = (100 + c.ColorID)
			
				-- SET FIRST GENERATION FAMILY BRANCH
				UPDATE f SET FamilyBranch=c.ChildOrder
					FROM #Family f JOIN (
					
					SELECT ParentsID, RowID, ISNULL(Age,0) AS Age, ROW_NUMBER() OVER (ORDER BY Age desc)*100 AS ChildOrder
					FROM #Family F 
						JOIN (SELECT SpousesID  FROM #Family WHERE Generation=100 AND SpousesID IS NOT NULL GROUP BY SpousesID) P1 ON F.ParentsID=p1.SpousesID
					WHERE Generation=200) c ON f.RowID=c.RowID
					
					UPDATE s SET FamilyBranch=fc.FamilyBranch
						FROM #Family fc
							JOIN #Family s ON fc.SpousesID=s.SpousesID
						WHERE s.FamilyBranch=0 AND fc.FamilyBranch>0
					
				
					
			DECLARE @FTO AS INT=1
			
				WHILE (SELECT COUNT(*) FROM #Family WHERE Generation=100 AND FamilyTree>=@FTO)>0
					BEGIN
						
						-- Set Person order for First Generation	
						UPDATE f SET HOrder=fo.PersonOrder
						FROM #Family f JOIN (
							SELECT RowID, ROW_NUMBER() OVER (ORDER BY SpousesID, Age desc) AS PersonOrder
							FROM #Family WHERE Generation=100 AND FamilyTree=@FTO) fo ON f.RowID=fo.RowID						
						
						SET @FTO=@FTO+1
						
					END
					
										
					
			UPDATE f SET SubFamily=NewBranch --SpousesID=NewBranch, 
			FROM #Family f 
				JOIN (
					SELECT SpousesID, ROW_NUMBER() OVER (ORDER BY SpousesID) AS NewBranch
					FROM #Family
					WHERE SpousesID IS NOT NULL 
						GROUP BY SpousesID
						) s ON f.SpousesID=s.SpousesID
			
				
				
		
				
				UPDATE c SET FamilyColor=p.SpouseColor
					FROM #Family c
						JOIN #Family p ON c.ParentsID=p.SpousesID
					
			
					
				UPDATE f SET FamilyOrder=fo.FamilyOrder
				FROM #Family f JOIN (
					SELECT FamilyBranch, ROW_NUMBER() OVER (ORDER BY FamilyBranch)*100 AS FamilyOrder
					FROM #Family WHERE Generation=100 GROUP BY FamilyBranch) fo ON f.FamilyBranch=fo.FamilyBranch		
					
				DECLARE @FamilyBranch AS INT=100
				
				SET @Generation=200
				WHILE (SELECT COUNT(*) FROM #Family WHERE Generation>=@Generation)>0
					BEGIN
										
						UPDATE c SET FamilyBranch=p.FamilyBranch
						FROM #Family c
							JOIN (
								SELECT SpousesID, FamilyBranch FROM #Family WHERE FamilyBranch > 0 
								GROUP BY SpousesID, FamilyBranch) p ON c.ParentsID=p.SpousesID
						WHERE c.FamilyBranch=0
						
						
						WHILE (SELECT COUNT(*) FROM #Family WHERE Generation=@Generation AND FamilyBranch>=@FamilyBranch)>0
						BEGIN
							
							-- Set Person order for First Generation	
							UPDATE f SET HOrder=fo.PersonOrder
							FROM #Family f JOIN (
								SELECT RowID, ROW_NUMBER() OVER (ORDER BY Age desc) AS PersonOrder
								FROM #Family WHERE Generation=@Generation AND FamilyBranch=@FamilyBranch) fo ON f.RowID=fo.RowID			
								
							SET @FamilyBranch=@FamilyBranch+ 100
						END
						
						SELECT @Generation=MIN(Generation), @FamilyBranch=100 FROM #Family WHERE Generation>@Generation	
						
					END
								

				
				
				
				
				
			/*
				
			DECLARE @HO AS INT=0
			DECLARE @G AS INT=100
		
				--UPDATE f 
				--SET @HO=HOrder=CASE WHEN Generation=@G
				--		THEN @HO + 1
				--		ELSE 1 END 
				--	, @G=Generation
					
				--FROM #Family f  
				DECLARE @PAR TABLE (RowID int, SpouseParentID int)
					
				WHILE (SELECT COUNT(*) FROM #Family WHERE Generation=@G)>0
					BEGIN
						DELETE @pAR
						
						INSERT @PAR(RowID, SpouseParentID)
						SELECT f.RowID, s.ParentsID
							FROM #Family f
								LEFT JOIN #Family s ON f.RowID=s.SpouseID
							WHERE f.ParentsID IS NULL AND f.Generation=@G
							
							UNION ALL
							
						SELECT f.RowID, F.ParentsID
							FROM #Family f
							WHERE f.ParentsID IS NOT NULL AND f.Generation=@G
						
						UPDATE @Par SET SpouseParentID=0 WHERE SpouseParentID IS null
						
						
						UPDATE h SET HOrder=ho.HOrder
						FROM #Family h JOIN (							
							SELECT f.RowID, ROW_NUMBER() OVER (ORDER BY par.SpouseParentID, SpousesID, Age DESC) AS HOrder
							FROM #Family f JOIN @Par par ON f.RowID=par.RowID
							WHERE Generation=@G) ho ON h.RowID=ho.RowID
						
						SET @G=@G+ 100
							SET @HO=0
					END	
			
				
			--UPDATE parent SET HOrder=child.ParentHOrder
			--FROM #Family parent
			--	JOIN (SELECT p.RowID, MIN(c.HOrder) AS ParentHOrder
			--			FROM #Family p
			--				JOIN #Family c ON p.RowID=c.ParentID1
			--			GROUP BY p.RowID) child ON parent.RowID=child.RowID
				
			--SELECT RowID, Person, FamilyBranch, FamilyColor, SpouseColor, ParentsID, Parent1, Parent2, hOrder, Generation
			--	FROM #Family	
			--ORDER BY Generation, hOrder	
*/	
			
			
			--DECLARE @Trees TABLE (FamilyTree int IDENTITY(1,1), SpousesID int)
			--	INSERT @Trees(SpousesID)
			--		SELECT SpousesID
			--		FROM #Family F 
			--			LEFT JOIN (SELECT ParentsID FROM #Family WHERE PersonID=@PersonID) cp ON f.SpousesID=cp.ParentsID
			--			WHERE Generation=100
			--		GROUP BY SpousesID
					
			
			--UPDATE f SET FamilyTree=t.FamilyTree
			--	FROM #Family f JOIN @Trees t ON f.SpousesID=t.SpousesID
			
			CREATE TABLE #FamilyTrees(RowID int, SpousesID int, FamilyTree INT, HOrder int)
								
			; WITH FamilyTree AS (
				SELECT NULL AS RowID, f.SpousesID, FamilyTree
				FROM #Family f WHERE Generation=100-- AND FamilyTree=1
					GROUP BY f.SpousesID, FamilyTree
				
					UNION ALL
				
				SELECT f.RowID, f.SpousesID, ft.FamilyTree
				FROM #Family f JOIN FamilyTree ft ON f.ParentsID=ft.SpousesID)
				
				
				INSERT #FamilyTrees (RowID, SpousesID, FamilyTree)
				SELECT * 
					FROM FamilyTree WHERE RowID IS NOT null
					
					UNION 
					
				SELECT RowID, SpousesID, FamilyTree FROM #Family WHERE Generation=100
		
				INSERT #FamilyTrees (RowID, SpousesID, FamilyTree)
				SELECT p1.RowID, p1.SpousesID, p2.FamilyTree
					FROM #FamilyTrees p1
						JOIN #FamilyTrees p2 ON p1.SpousesID=p2.SpousesID
					WHERE p1.FamilyTree<>p2.FamilyTree
					
					UNION 
					
				SELECT p2.RowID, p2.SpousesID, p1.FamilyTree
					FROM #FamilyTrees p1
						JOIN #FamilyTrees p2 ON p1.SpousesID=p2.SpousesID
					WHERE p1.FamilyTree<>p2.FamilyTree
				
				
							
				--SELECT f.*
				--	FROM FamilyTree f JOIN #family m ON f.RowID=m.RowID
				--ORDER BY FamilyTree
				
				
					
			--SELECT ft.FamilyTree, Generation, HOrder, f.RowID, f.Person, FamilyBranch, SubFamily, FamilyColor, FamilyOrder, ParentsID
			--	, Parent1, Parent2, f.SpousesID, Children, Spouse, SPouseColor, Age
			--FROM #fAMILY f	
			--		JOIN @FamilyTrees ft ON f.RowID=ft.RowID
			--	ORDER BY ft.FamilyTree, Generation, HOrder
					
					
			SELECT ft.FamilyTree, Generation, HOrder, f.RowID, PersonID
				, @FamilyID AS FamilyID, f.SpousesID AS SpouseBranch, SpouseColor, FamilyBranch, FamilyColor
				, Person, Gender, Age, BirthDate, DateOfDeath, Comments
				, LastName, FirstName
				, CASE 
					WHEN ContactID IS NULL 
					THEN REPLACE(ABI.CORE.GetEditURL('SavantAdvisor','FamilyMembers'),'[eID]',eID)
					ELSE REPLACE(ABI.CORE.GetEditURL('SavantAdvisor','FamilyMemberCRM'),'[eID]',eID) 
					END AS EditDetails
				, REPLACE(REPLACE(ABI.CORE.GetJobURL('SavantAdvisor','FamilyDeleteMember'),'[Parameters]','@PersonID=''[PersonID]'''),'[PersonID]',PersonID) AS DeletePerson	
				, CASE 
					WHEN ContactID IS NOT NULL THEN SavantAdvisor.CRM.ViewRecord(ContactID,'Contact') END AS ViewCRM
				, REPLACE(ABI.CORE.GetReportURL('SavantAdvisor','FamilyNewMember',0),'[PersonID]',PersonID) AS AddFamilyMember
				
				
			FROM #Family f JOIN #FamilyTrees ft ON f.RowID=ft.RowID
			ORDER BY ft.FamilyTree, Generation, FamilyBranch, HOrder, RowID
GO


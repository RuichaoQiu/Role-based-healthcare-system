

INSERT INTO officevisits(ID, visitDate, HCPID, notes, PatientID, HospitalID)
VALUES
(107, '2011-07-26', 9000000000, 'Diagnose Influenza', 2, '1'),
(108, '2011-07-26', 9000000000, 'Diagnose Influenza', 2, '1'),
(109, '2011-07-26', 9000000000, 'Diagnose Influenza', 2, '1'),
(110, '2011-07-26', 9000000000, 'Diagnose Influenza', 2, '1'),
(112, '2011-07-26', 9000000000, 'Diagnose Influenza', 2, '1'),

(113, '2011-08-05', 9000000000, 'Diagnose Influenza', 2, '1'),
(114, '2011-08-05', 9000000000, 'Diagnose Influenza', 2, '1'),
(115, '2011-08-04', 9000000000, 'Diagnose Influenza', 2, '1'),
(116, '2011-08-05', 9000000000, 'Diagnose Influenza', 2, '1'),
(117, '2011-08-05', 9000000000, 'Diagnose Influenza', 2, '1'),
(106, '2011-08-05', 9000000000, 'Diagnose Influenza', 2, '1'),

(118, '2007-01-01', 9000000000, 'Diagnose Malaria', 2, '1'),
(119, '2007-01-10', 9000000000, 'Diagnose Malaria', 2, '1'),
(120, '2008-01-02', 9000000004, 'Diagnose Malaria', 2, '1'),
(121, '2008-01-03', 9000000000, 'Diagnose Malaria', 2, '1'),
(125, '2008-01-10', 9000000000, 'Diagnose Malaria', 2, '1'),
(126, '2008-01-09', 9000000000, 'Diagnose Malaria', 2, '1'),
(127, '2008-01-01', 9000000004, 'Diagnose Malaria', 2, '1'),
(128, '2008-08-02', 9000000000, 'Diagnose Malaria', 2, '1'),

(145, '2011-08-25', 9000000000, 'Diagnose Mumps', 2, '1'),
(123, '2011-09-09', 9000000000, 'Diagnose Mumps', 3, '1'),
(124, '2011-09-12', 9000000000, 'Diagnose Mumps', 88, '1')

ON DUPLICATE KEY UPDATE id = id;

INSERT INTO ovdiagnosis(ID, VisitID, ICDCode)
VALUES
(106, 106, 487.00),
(107, 107, 487.00),
(108, 108, 487.00),
(109, 109, 487.00),
(110, 110, 487.00),
(112, 112, 487.00),
(113, 113, 487.00),

(114, 114, 487.00),
(115, 115, 487.00),
(116, 116, 487.00),
(117, 117, 487.00),

(118, 118, 84.50),
(119, 119, 84.50),
(120, 120, 84.50),
(121, 121, 84.50),
(125, 125, 84.50),
(126, 126, 84.50),
(127, 127, 84.50),
(128, 128, 84.50),

(145, 145, 72.00),
(123, 123, 72.00),
(124, 124, 72.00)
ON DUPLICATE KEY UPDATE VisitID = VALUES(VisitID), ICDCode = VALUES(ICDCode);

INSERT INTO labprocedure (PatientMID, LaboratoryProcedureCode, Rights, Status, Commentary, Results, OfficeVisitID, UpdatedDate,
                          NumericalResults, NumericalResultsUnit, LowerBound, UpperBound)
VALUES
(4, '10640-1','ALLOWED','Completed','','',109,'2008-01-22 20:20:45.0', '10', 'g', '9', '11');

INSERT INTO ovmedication(ID, VisitID, NDCode, StartDate, EndDate, Dosage, Instructions)
VALUES
(4, 107, '009042407', '2007-03-10', '2007-04-10', 5, 'Take twice daily'),
(5, 108, '009042407', '2007-03-11', '2007-04-11', 5, 'Take twice daily')
ON DUPLICATE KEY UPDATE id = id;

INSERT INTO ovsurvey(VisitID, SurveyDate, WaitingRoomMinutes, ExamRoomMinutes, VisitSatisfaction, TreatmentSatisfaction)
VALUES
(107, '2008-09-25 02:00:00.0', 15, 10, 2, 4),
(108, '2008-09-25 03:00:00.0', 17, 25, 4, 4)
ON DUPLICATE KEY UPDATE VisitID = VisitID;

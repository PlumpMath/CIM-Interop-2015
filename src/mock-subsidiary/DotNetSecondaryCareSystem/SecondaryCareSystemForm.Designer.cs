namespace DotNetSecondaryCareSystem
{
    partial class SecondaryCareSystemForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(SecondaryCareSystemForm));
            this.panel2 = new System.Windows.Forms.Panel();
            this.panel1 = new System.Windows.Forms.Panel();
            this.label3 = new System.Windows.Forms.Label();
            this.tbCimUrl = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.pictureBox1 = new System.Windows.Forms.PictureBox();
            this.label1 = new System.Windows.Forms.Label();
            this.toolStrip1 = new System.Windows.Forms.ToolStrip();
            this.tabControl1 = new System.Windows.Forms.TabControl();
            this.tabPage1 = new System.Windows.Forms.TabPage();
            this.label4 = new System.Windows.Forms.Label();
            this.tbGetDemographicsResult = new System.Windows.Forms.TextBox();
            this.tbNhsNumber = new System.Windows.Forms.TextBox();
            this.btnGetDemographics = new System.Windows.Forms.Button();
            this.tabPage2 = new System.Windows.Forms.TabPage();
            this.label5 = new System.Windows.Forms.Label();
            this.tbGetFullRecordResponse = new System.Windows.Forms.TextBox();
            this.tbPatientGuid = new System.Windows.Forms.TextBox();
            this.btnGetFullRecord = new System.Windows.Forms.Button();
            this.lblGetDemographicsFhirUrl = new System.Windows.Forms.Label();
            this.label7 = new System.Windows.Forms.Label();
            this.label8 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.label9 = new System.Windows.Forms.Label();
            this.lblGetFullRecordFhirUrl = new System.Windows.Forms.Label();
            this.panel2.SuspendLayout();
            this.panel1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).BeginInit();
            this.tabControl1.SuspendLayout();
            this.tabPage1.SuspendLayout();
            this.tabPage2.SuspendLayout();
            this.SuspendLayout();
            // 
            // panel2
            // 
            this.panel2.BackColor = System.Drawing.Color.Silver;
            this.panel2.Controls.Add(this.panel1);
            this.panel2.Dock = System.Windows.Forms.DockStyle.Top;
            this.panel2.Location = new System.Drawing.Point(0, 0);
            this.panel2.Name = "panel2";
            this.panel2.Padding = new System.Windows.Forms.Padding(0, 0, 0, 1);
            this.panel2.Size = new System.Drawing.Size(1617, 93);
            this.panel2.TabIndex = 2;
            // 
            // panel1
            // 
            this.panel1.BackColor = System.Drawing.Color.White;
            this.panel1.Controls.Add(this.label3);
            this.panel1.Controls.Add(this.tbCimUrl);
            this.panel1.Controls.Add(this.label2);
            this.panel1.Controls.Add(this.pictureBox1);
            this.panel1.Controls.Add(this.label1);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel1.Font = new System.Drawing.Font("Segoe UI", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.panel1.Location = new System.Drawing.Point(0, 0);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(1617, 92);
            this.panel1.TabIndex = 1;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("Segoe UI", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label3.Location = new System.Drawing.Point(1252, 54);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(53, 15);
            this.label3.TabIndex = 6;
            this.label3.Text = "CIM URL";
            // 
            // tbCimUrl
            // 
            this.tbCimUrl.Location = new System.Drawing.Point(1322, 51);
            this.tbCimUrl.Name = "tbCimUrl";
            this.tbCimUrl.Size = new System.Drawing.Size(260, 23);
            this.tbCimUrl.TabIndex = 6;
            this.tbCimUrl.Text = "http://localhost:8080/";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Segoe UI", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.Location = new System.Drawing.Point(345, 48);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(252, 15);
            this.label2.TabIndex = 2;
            this.label2.Text = "For the Common Interface Mechanism Project";
            // 
            // pictureBox1
            // 
            this.pictureBox1.Image = ((System.Drawing.Image)(resources.GetObject("pictureBox1.Image")));
            this.pictureBox1.Location = new System.Drawing.Point(3, 3);
            this.pictureBox1.Name = "pictureBox1";
            this.pictureBox1.Size = new System.Drawing.Size(305, 60);
            this.pictureBox1.SizeMode = System.Windows.Forms.PictureBoxSizeMode.AutoSize;
            this.pictureBox1.TabIndex = 1;
            this.pictureBox1.TabStop = false;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Segoe UI", 18F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(342, 9);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(599, 32);
            this.label1.TabIndex = 0;
            this.label1.Text = "Secondary Care System Demonstrator (CIM Consumer)";
            // 
            // toolStrip1
            // 
            this.toolStrip1.Location = new System.Drawing.Point(0, 93);
            this.toolStrip1.Name = "toolStrip1";
            this.toolStrip1.Size = new System.Drawing.Size(1617, 25);
            this.toolStrip1.TabIndex = 7;
            this.toolStrip1.Text = "toolStrip1";
            // 
            // tabControl1
            // 
            this.tabControl1.Controls.Add(this.tabPage1);
            this.tabControl1.Controls.Add(this.tabPage2);
            this.tabControl1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tabControl1.Location = new System.Drawing.Point(0, 118);
            this.tabControl1.Name = "tabControl1";
            this.tabControl1.SelectedIndex = 0;
            this.tabControl1.Size = new System.Drawing.Size(1617, 742);
            this.tabControl1.TabIndex = 8;
            // 
            // tabPage1
            // 
            this.tabPage1.Controls.Add(this.label8);
            this.tabPage1.Controls.Add(this.label7);
            this.tabPage1.Controls.Add(this.lblGetDemographicsFhirUrl);
            this.tabPage1.Controls.Add(this.label4);
            this.tabPage1.Controls.Add(this.tbGetDemographicsResult);
            this.tabPage1.Controls.Add(this.tbNhsNumber);
            this.tabPage1.Controls.Add(this.btnGetDemographics);
            this.tabPage1.Location = new System.Drawing.Point(4, 24);
            this.tabPage1.Name = "tabPage1";
            this.tabPage1.Padding = new System.Windows.Forms.Padding(3);
            this.tabPage1.Size = new System.Drawing.Size(1609, 714);
            this.tabPage1.TabIndex = 0;
            this.tabPage1.Text = "Get demographics by NHS number";
            this.tabPage1.UseVisualStyleBackColor = true;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(17, 16);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(76, 15);
            this.label4.TabIndex = 7;
            this.label4.Text = "NHS number";
            // 
            // tbGetDemographicsResult
            // 
            this.tbGetDemographicsResult.Font = new System.Drawing.Font("Consolas", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.tbGetDemographicsResult.Location = new System.Drawing.Point(20, 102);
            this.tbGetDemographicsResult.Multiline = true;
            this.tbGetDemographicsResult.Name = "tbGetDemographicsResult";
            this.tbGetDemographicsResult.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
            this.tbGetDemographicsResult.Size = new System.Drawing.Size(806, 422);
            this.tbGetDemographicsResult.TabIndex = 6;
            // 
            // tbNhsNumber
            // 
            this.tbNhsNumber.Location = new System.Drawing.Point(110, 13);
            this.tbNhsNumber.Name = "tbNhsNumber";
            this.tbNhsNumber.Size = new System.Drawing.Size(383, 23);
            this.tbNhsNumber.TabIndex = 3;
            this.tbNhsNumber.Text = "6936548122";
            // 
            // btnGetDemographics
            // 
            this.btnGetDemographics.Location = new System.Drawing.Point(514, 10);
            this.btnGetDemographics.Name = "btnGetDemographics";
            this.btnGetDemographics.Size = new System.Drawing.Size(132, 27);
            this.btnGetDemographics.TabIndex = 4;
            this.btnGetDemographics.Text = "Get demographics";
            this.btnGetDemographics.UseVisualStyleBackColor = true;
            this.btnGetDemographics.Click += new System.EventHandler(this.btnGetDemographics_Click);
            // 
            // tabPage2
            // 
            this.tabPage2.Controls.Add(this.label6);
            this.tabPage2.Controls.Add(this.label9);
            this.tabPage2.Controls.Add(this.lblGetFullRecordFhirUrl);
            this.tabPage2.Controls.Add(this.label5);
            this.tabPage2.Controls.Add(this.tbGetFullRecordResponse);
            this.tabPage2.Controls.Add(this.tbPatientGuid);
            this.tabPage2.Controls.Add(this.btnGetFullRecord);
            this.tabPage2.Location = new System.Drawing.Point(4, 24);
            this.tabPage2.Name = "tabPage2";
            this.tabPage2.Padding = new System.Windows.Forms.Padding(3);
            this.tabPage2.Size = new System.Drawing.Size(1609, 714);
            this.tabPage2.TabIndex = 1;
            this.tabPage2.Text = "Get full record by Patient GUID";
            this.tabPage2.UseVisualStyleBackColor = true;
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(17, 16);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(74, 15);
            this.label5.TabIndex = 11;
            this.label5.Text = "Patient GUID";
            // 
            // tbGetFullRecordResponse
            // 
            this.tbGetFullRecordResponse.Font = new System.Drawing.Font("Consolas", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.tbGetFullRecordResponse.Location = new System.Drawing.Point(20, 102);
            this.tbGetFullRecordResponse.Multiline = true;
            this.tbGetFullRecordResponse.Name = "tbGetFullRecordResponse";
            this.tbGetFullRecordResponse.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
            this.tbGetFullRecordResponse.Size = new System.Drawing.Size(806, 422);
            this.tbGetFullRecordResponse.TabIndex = 10;
            // 
            // tbPatientGuid
            // 
            this.tbPatientGuid.Location = new System.Drawing.Point(110, 13);
            this.tbPatientGuid.Name = "tbPatientGuid";
            this.tbPatientGuid.Size = new System.Drawing.Size(383, 23);
            this.tbPatientGuid.TabIndex = 8;
            this.tbPatientGuid.Text = "00b87973-f3a3-4db6-a4d9-a559db2660e7";
            // 
            // btnGetFullRecord
            // 
            this.btnGetFullRecord.Location = new System.Drawing.Point(514, 10);
            this.btnGetFullRecord.Name = "btnGetFullRecord";
            this.btnGetFullRecord.Size = new System.Drawing.Size(132, 27);
            this.btnGetFullRecord.TabIndex = 9;
            this.btnGetFullRecord.Text = "Get full record";
            this.btnGetFullRecord.UseVisualStyleBackColor = true;
            this.btnGetFullRecord.Click += new System.EventHandler(this.btnGetFullRecord_Click);
            // 
            // lblGetDemographicsFhirUrl
            // 
            this.lblGetDemographicsFhirUrl.AutoSize = true;
            this.lblGetDemographicsFhirUrl.Font = new System.Drawing.Font("Consolas", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblGetDemographicsFhirUrl.Location = new System.Drawing.Point(107, 44);
            this.lblGetDemographicsFhirUrl.Name = "lblGetDemographicsFhirUrl";
            this.lblGetDemographicsFhirUrl.Size = new System.Drawing.Size(273, 14);
            this.lblGetDemographicsFhirUrl.TabIndex = 8;
            this.lblGetDemographicsFhirUrl.Text = "v0.1/A99999/Patient?identifier=NHS|{0}";
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(17, 44);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(56, 15);
            this.label7.TabIndex = 9;
            this.label7.Text = "FHIR URL";
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Location = new System.Drawing.Point(17, 84);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(57, 15);
            this.label8.TabIndex = 10;
            this.label8.Text = "Response";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(17, 84);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(57, 15);
            this.label6.TabIndex = 18;
            this.label6.Text = "Response";
            // 
            // label9
            // 
            this.label9.AutoSize = true;
            this.label9.Location = new System.Drawing.Point(17, 44);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(56, 15);
            this.label9.TabIndex = 17;
            this.label9.Text = "FHIR URL";
            // 
            // lblGetFullRecordFhirUrl
            // 
            this.lblGetFullRecordFhirUrl.AutoSize = true;
            this.lblGetFullRecordFhirUrl.Font = new System.Drawing.Font("Consolas", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblGetFullRecordFhirUrl.Location = new System.Drawing.Point(107, 44);
            this.lblGetFullRecordFhirUrl.Name = "lblGetFullRecordFhirUrl";
            this.lblGetFullRecordFhirUrl.Size = new System.Drawing.Size(308, 14);
            this.lblGetFullRecordFhirUrl.TabIndex = 16;
            this.lblGetFullRecordFhirUrl.Text = "v0.1/A99999/Patient/{0}/$everythingnobinary";
            // 
            // SecondaryCareSystemForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1617, 860);
            this.Controls.Add(this.tabControl1);
            this.Controls.Add(this.toolStrip1);
            this.Controls.Add(this.panel2);
            this.Font = new System.Drawing.Font("Segoe UI", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "SecondaryCareSystemForm";
            this.Text = "Secondary Care System Demonstrator v0.1";
            this.panel2.ResumeLayout(false);
            this.panel1.ResumeLayout(false);
            this.panel1.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).EndInit();
            this.tabControl1.ResumeLayout(false);
            this.tabPage1.ResumeLayout(false);
            this.tabPage1.PerformLayout();
            this.tabPage2.ResumeLayout(false);
            this.tabPage2.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Panel panel2;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.PictureBox pictureBox1;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox tbCimUrl;
        private System.Windows.Forms.ToolStrip toolStrip1;
        private System.Windows.Forms.TabControl tabControl1;
        private System.Windows.Forms.TabPage tabPage1;
        private System.Windows.Forms.TextBox tbGetDemographicsResult;
        private System.Windows.Forms.TextBox tbNhsNumber;
        private System.Windows.Forms.Button btnGetDemographics;
        private System.Windows.Forms.TabPage tabPage2;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.TextBox tbGetFullRecordResponse;
        private System.Windows.Forms.TextBox tbPatientGuid;
        private System.Windows.Forms.Button btnGetFullRecord;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.Label lblGetDemographicsFhirUrl;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label label9;
        private System.Windows.Forms.Label lblGetFullRecordFhirUrl;
    }
}


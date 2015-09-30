using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Xml;
using System.Xml.Linq;
using System.Xml.Schema;
using System.Xml.Serialization;

namespace DotNetGPSystem
{
    internal static class Utilities
    {
        public static bool IsValidXml(string xml, XmlSchemaSet schemas, out string[] validationErrors)
        {
            List<string> validationErrorsList = new List<string>();

            XmlReaderSettings xmlReaderSettings = new XmlReaderSettings();

            xmlReaderSettings.ValidationType = ValidationType.Schema;
            xmlReaderSettings.ValidationFlags |= XmlSchemaValidationFlags.ProcessSchemaLocation | XmlSchemaValidationFlags.ReportValidationWarnings;

            xmlReaderSettings.ValidationEventHandler += (sender, e) =>
            {
                validationErrorsList.Add(string.Format("[{0}] {1}  Exception: {2}", e.Severity.ToString().ToUpper(), e.Message, e.Exception.Message));
            };

            xmlReaderSettings.Schemas = schemas;
            try
            {
                using (StringReader xmlStringReader = new StringReader(xml))
                    using (XmlReader xmlValidatingReader = XmlReader.Create(xmlStringReader, xmlReaderSettings))
                        while (xmlValidatingReader.Read()) ;
            }
            catch (Exception e)
            {
                validationErrorsList.Add(string.Format("[EXCEPTION] {0}", e.Message));
            }

            validationErrors = validationErrorsList.ToArray();

            return (validationErrors.Length == 0);
        }

        public static XmlSchemaSet LoadSchemaResources(string[] resourceNames)
        {
            XmlSchemaSet schemas = new XmlSchemaSet();

            foreach (string resourceName in resourceNames)
            {
                Stream stream = Assembly.GetExecutingAssembly().GetManifestResourceStream(resourceName);

                XmlTextReader reader = new XmlTextReader(stream);

                XmlSchema schema = XmlSchema.Read(reader, (sender, e) =>
                {
                    throw e.Exception;
                });

                schemas.Add(schema);
            }

            schemas.Compile();

            return schemas;
        }

        public static string LoadStringResource(string resourceName)
        {
            using (Stream stream = Assembly.GetExecutingAssembly().GetManifestResourceStream(resourceName))
            {
                StreamReader reader = new StreamReader(stream);

                return reader.ReadToEnd();
            }
        }

        public static T Deserialize<T>(string xml) where T : new()
        {
            XmlSerializer serializer = new XmlSerializer(typeof(T));

            using (StringReader reader = new StringReader(xml))
                return (T)serializer.Deserialize(reader);
        }

        public static string Serialize<T>(T item)
        {
            XmlSerializer serializer = new XmlSerializer(typeof(T));

            using (Utf8StringWriter writer = new Utf8StringWriter())
            {
                serializer.Serialize(writer, item);
                return writer.ToString();
            }
        }

        public static string FormatXml(string xml)
        {
            try
            {
                XDocument doc = XDocument.Parse(xml);
                return doc.ToString();
            }
            catch (Exception)
            {
                return xml;
            }
        }

        public static R WhenNotNull<T, R>(this T source, Func<T, R> selector)
        {
            if (source == null)
                return default(R);

            return selector(source);
        }

        public static IEnumerable<TSource> DistinctBy<TSource, TKey>(this IEnumerable<TSource> source, Func<TSource, TKey> keySelector)
        {
            HashSet<TKey> seenKeys = new HashSet<TKey>();

            foreach (TSource element in source)
            {
                if (seenKeys.Add(keySelector(element)))
                {
                    yield return element;
                }
            }
        }

        public static DataGridViewRow CloneDataGridViewRow(this DataGridView dataGridView, FontStyle? fontStyle = null, Color? backColor = null, DataGridViewContentAlignment? alignment = null)
        {
            DataGridViewRow row = (DataGridViewRow)dataGridView.RowTemplate.Clone();

            if (backColor != null)
                row.DefaultCellStyle.BackColor = backColor.Value;

            if (fontStyle != null)
                row.DefaultCellStyle.Font = new Font(row.DefaultCellStyle.Font, fontStyle.Value);

            if (alignment != null)
                row.DefaultCellStyle.Alignment = alignment.Value;

            row.CreateCells(dataGridView);

            return row;
        }

        public static void PopulateComboBox<T>(this ComboBox comboBox, IEnumerable<T> list, Func<T, string> displaySelector, string emptyItemDisplayText = null) where T : class
        {
            comboBox.DisplayMember = "DisplayMember";
            comboBox.ValueMember = "ValueMember";
            
            var comboBoxList = list.Select(t => new { DisplayMember = displaySelector(t), ValueMember = t }).ToList();

            if (emptyItemDisplayText != null)
                comboBoxList.Insert(0, new { DisplayMember = emptyItemDisplayText, ValueMember = (T)null });

            comboBox.DataSource = comboBoxList.ToArray();
        }

        public static void CreateColumn(this TableLayoutPanel tableLayoutPanel, ColumnStyle columnStyle)
        {
            tableLayoutPanel.ColumnCount++;
            tableLayoutPanel.ColumnStyles.Add(columnStyle);
        }
    }
}
